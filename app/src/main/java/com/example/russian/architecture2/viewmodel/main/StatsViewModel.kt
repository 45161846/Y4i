package com.example.russian.architecture2.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.russian.MyEnumClasses.ScreenFilters
import com.example.russian.MyEnumClasses.ScreenStats
import com.example.russian.MyEnumClasses.SortType
import com.example.russian.MyEnumClasses.SortTypeMode
import com.example.russian.MyEnumClasses.SortTypesEnum
import com.example.russian.MyEnumClasses.getDisplayableName
import com.example.russian.architecture2.backend.data.dao.StatsDao
import com.example.russian.architecture2.mapper.WordMapper
import com.example.russian.architecture2.repository.arch.StatsScreenRepositoryInterface
import com.example.russian.architecture2.repository.impl.StatsScreenRepository
import com.example.russian.architecture2.ui.actions.MyActions
import com.example.russian.architecture2.ui.draw.stats.comp.PlaylistState
import com.example.russian.architecture2.ui.draw.stats.comp.PlaylistViewState
import com.example.russian.architecture2.ui.draw.stats.comp.SortFilterState
import com.example.russian.architecture2.ui.draw.stats.comp.SortFilterViewState
import com.example.russian.architecture2.ui.draw.stats.comp.UnansweredViewState
import com.example.russian.architecture2.ui.draw.stats.screen.FilterScreenActions
import com.example.russian.architecture2.ui.draw.stats.screen.StatsScreenActions
import com.example.russian.architecture2.ui.draw.test.testPlaylistState
import com.example.russian.architecture2.ui.draw.test.testShowUnansweredState
import com.example.russian.architecture2.ui.draw.test.testSortState
import com.example.russian.architecture2.ui.state.FilterScreenData
import com.example.russian.architecture2.ui.state.FilterSettingData
import com.example.russian.architecture2.ui.state.MarkedPlaylist
import com.example.russian.architecture2.ui.state.StatsFirstScreenState
import com.example.russian.toolPackage.mergeOldNewPlaylist
import com.example.russian.ui.theme.PrimaryBackground
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

class StatsViewModel : ViewModel(), StatsViewModelAPI {

    private val _uiState = MutableStateFlow<StatsFirstScreenState>(StatsFirstScreenState.Loading)
    private val uiState: StateFlow<StatsFirstScreenState> = _uiState

    private val _filterPlaylistState = MutableStateFlow(testPlaylistState())
    private val _filterSortTypeState = MutableStateFlow(testSortState())
    private val _filterAnswerState = MutableStateFlow(testShowUnansweredState())


    private val filterScreenData = FilterScreenData(
        _filterPlaylistState,
        _filterSortTypeState,
        _filterAnswerState
    )

    private val repo: StatsScreenRepositoryInterface = StatsScreenRepository()

    private lateinit var statsParams: StatsParametersAPI
    private val filterSettingFlow = MutableStateFlow(defaultFilterSettings())

    private val searchPrefFlow = MutableStateFlow("")


    override fun uiState(): StateFlow<StatsFirstScreenState> = uiState
    override fun uiFilterData(): FilterScreenData = filterScreenData
    override fun actions(screenType: Any, navController: NavController): MyActions {

        return when (screenType) {
            is ScreenStats -> StatsScreenActions { search(it) }
            is ScreenFilters -> FilterScreenActions(
                onResetClick = {
                    val defaultData = defaultFilterSettings()
                    _filterPlaylistState.value = PlaylistViewState(
                        _filterPlaylistState.value.playlistStates.map {
                            PlaylistState(it.title, true)
                        }
                    )
                    _filterAnswerState.value = UnansweredViewState(defaultData.showUnanswered)
                    _filterSortTypeState.value = SortFilterViewState(
                        defaultData.sortTypes.mapIndexed { index, it ->
                            SortFilterState(
                                getDisplayableName(it.type),
                                it.mode,
                                index == defaultData.sortTypes.lastIndex
                            )
                        }
                    )
                },
                onPlaylistClick = { ind ->
                    val playlists = _filterPlaylistState.value.playlistStates
                    _filterPlaylistState.value = PlaylistViewState(
                        playlists.mapIndexed { index, playlist ->
                            val checked =
                                if (ind == index) playlist.checked.not() else playlist.checked
                            PlaylistState(playlist.title, checked)
                        }
                    )

                },
                onSortTypeClick = { ind ->
                    val prev = _filterSortTypeState.value.states

                    _filterSortTypeState.value = SortFilterViewState(
                        prev.subList(0, prev.lastIndex + 1).mapIndexed { index, it ->

                            SortFilterState(
                                it.name,
                                if (ind == index) it.mode.nextMode() else SortTypeMode.UNSPECIFIED,
                                index == prev.lastIndex
                            )
                        }
                    )
                },
                onShowUnansweredClick = {
                    val a = _filterAnswerState.value.show
                    _filterAnswerState.value = UnansweredViewState(a.not())
                },
                onSaveClick = {
                    navController.navigate(ScreenStats)
                    filterSettingFlow.value = FilterSettingData(
                        filterSettingFlow.value.playlists.mapIndexed{index, markedPlaylist ->
                            MarkedPlaylist(markedPlaylist.playlist, _filterPlaylistState.value.playlistStates[index].checked)
                        },
                        filterSettingFlow.value.sortTypes.mapIndexed{index, sortType ->
                            SortType(sortType.type, _filterSortTypeState.value.states[index].mode)
                        },
                        _filterAnswerState.value.show
                    )
                }
            )
            //can never happen
            else -> throw RuntimeException("Unknown type of screen: ${screenType.javaClass.name}")
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun setDao(statsDao: StatsDao) {
        repo.setDao(statsDao)

        //TODO
        //get from app settings
        statsParams = StatsParametersAPIImpl(showWinRate = true, showWinRateIndicator = false)

        val filterDataFlow = repo.allPlaylistsFlow()
            .combine(filterSettingFlow) { playlists, filterSettingData ->
                val marked = mergeOldNewPlaylist(filterSettingData.playlists, playlists)
                val filterData = FilterSettingData(
                    marked,
                    filterSettingData.sortTypes,
                    filterSettingData.showUnanswered
                )

                _filterPlaylistState.value = PlaylistViewState(marked.map {
                    PlaylistState(
                        it.playlist.title,
                        it.marked
                    )
                })
                _filterSortTypeState.value = SortFilterViewState(filterSettingData.sortTypes.mapIndexed {index, it ->
                    SortFilterState(getDisplayableName(it.type), it.mode, index == filterData.sortTypes.lastIndex)
                })
                _filterAnswerState.value = UnansweredViewState(filterSettingData.showUnanswered)

                filterData
            }
            .flatMapMerge { filterData ->
                filterSettingFlow.value = filterData
                repo.wordsFiltered(filterData)
            }

        combine(searchPrefFlow, filterDataFlow) { pref, words ->
            val filteredWords = words.filter {
                it.displayableText.startsWith(pref)
            }
            if (filteredWords.isEmpty()) {
                _uiState.value = StatsFirstScreenState.NothingFound {
                    search(it)
                }
            } else {
                _uiState.value = StatsFirstScreenState.Success(
                    WordMapper.wordListToCards(filteredWords, statsParams),
                    PrimaryBackground,
                    onSearch = { prefix ->
                        search(prefix)
                    }
                )
            }
        }
            .launchIn(viewModelScope)
    }


    private fun search(pref: String) {
        viewModelScope.launch {
            searchPrefFlow.value = pref
        }
    }

    override fun setStatsParams(parametersAPI: StatsParametersAPI) {
        this.statsParams = StatsParametersAPIImpl(
            parametersAPI.showWinRate(),
            parametersAPI.showWinRateIndicator()
        )
    }
}

interface StatsParametersAPI {
    fun showWinRate(): Boolean
    fun showWinRateIndicator(): Boolean
}

private class StatsParametersAPIImpl(
    private var showWinRate: Boolean,
    private var showWinRateIndicator: Boolean
) : StatsParametersAPI {
    override fun showWinRate(): Boolean = showWinRate

    override fun showWinRateIndicator(): Boolean = showWinRateIndicator
}

private fun defaultFilterSettings() = FilterSettingData(
    emptyList(),
    listOf(
        SortType(SortTypesEnum.ALPHABETICAL, SortTypeMode.DIRECT),
        SortType(SortTypesEnum.WIN_RATE)
    ),
    true
)
