package com.example.russian.main.viewmodel.main

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.navigation.NavController
import com.example.russian.architectured.stats.StatsScreenActions
import com.example.russian.main.application.MyApplication
import com.example.russian.main.back.data.dao.StatsDao
import com.example.russian.main.back.data.entity.Statistics
import com.example.russian.main.back.data.entity.playlist.Playlist
import com.example.russian.main.enums.ScreenFilters
import com.example.russian.main.enums.ScreenStats
import com.example.russian.main.enums.SortType
import com.example.russian.main.enums.SortTypeMode
import com.example.russian.main.enums.SortTypesEnum
import com.example.russian.main.enums.getDisplayableName
import com.example.russian.main.mapper.WordMapper
import com.example.russian.main.repository.arch.StatsScreenRepositoryInterface
import com.example.russian.main.repository.impl.StatsScreenRepository
import com.example.russian.main.tool.mergeOldNewPlaylist
import com.example.russian.main.ui.draw.Source
import com.example.russian.main.ui.draw.common.SimpleBooleanState
import com.example.russian.main.ui.draw.practice.PracScreenActions
import com.example.russian.main.ui.draw.settings.SettingActions
import com.example.russian.main.ui.draw.settings.SettingScreenData
import com.example.russian.main.ui.draw.settings.SwitchState
import com.example.russian.main.ui.draw.settings.TestStatsCardState
import com.example.russian.main.ui.draw.stats.comp.PlaylistState
import com.example.russian.main.ui.draw.stats.comp.PlaylistViewStateParent
import com.example.russian.main.ui.draw.stats.comp.SortFilterState
import com.example.russian.main.ui.draw.stats.comp.SortFilterViewState
import com.example.russian.main.ui.draw.stats.screen.FilterScreenActions
import com.example.russian.main.ui.draw.test.testShowUnansweredState
import com.example.russian.main.ui.draw.test.testSortState
import com.example.russian.main.ui.state.FilterScreenData
import com.example.russian.main.ui.state.FilterSettingData
import com.example.russian.main.ui.state.MarkedPlaylist
import com.example.russian.main.ui.state.PracScreenStage
import com.example.russian.main.ui.state.StatsFirstScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import org.burnoutcrew.reorderable.ItemPosition

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModel constructor(
    sharedPreferences: SharedPreferences,
    statsDao: StatsDao
) : ViewModel(), MainViewModelAPI {
    private val googleFormURI: String =
        "https://docs.google.com/forms/d/e/1FAIpQLSflCnwcS_yvTSpa5Ad4VYRSeARjIvyrs0zmu0dQg0elXv9Pjw/viewform?usp=sf_link"

    private lateinit var activityStarter: ActivityStarter

    private val _uiGeneralState =
        MutableStateFlow<StatsFirstScreenState>(StatsFirstScreenState.Loading)
    private val uiPracLocalState: StateFlow<StatsFirstScreenState> = _uiGeneralState


    private val filterScreenData = FilterScreenData(
        MutableStateFlow(
            PlaylistViewStateParent.Loading
        ),
        MutableStateFlow(testSortState()),
        MutableStateFlow(testShowUnansweredState())
    )

    private var _playlists: List<Playlist> by mutableStateOf(emptyList())

    private val _pracLocalUiState = MutableStateFlow<PracScreenStage>(PracScreenStage.Local.Loading)
    val pracScreenLocalUiState: StateFlow<PracScreenStage> = _pracLocalUiState

    private val _pracRemoteUiState =
        MutableStateFlow<PracScreenStage>(PracScreenStage.Remote.Loading)
    val pracScreenRemoteUiState: StateFlow<PracScreenStage> = _pracRemoteUiState

    var currentScreen: Source = Source.Local

    val pracActions = PracScreenActions(
        onPlaylistMove = this::onMovePlaylist,
    )

    private val repo: StatsScreenRepositoryInterface = StatsScreenRepository(
        statsDao
    )

    private lateinit var settingsScreenData: SettingScreenData
    private lateinit var settings: DisplaySettings

    private val filterSettingFlow = MutableStateFlow(defaultFilterSettings())

    private val searchPrefFlow = MutableStateFlow("")


    override fun uiState(): StateFlow<StatsFirstScreenState> = uiPracLocalState
    override fun uiFilterData(): FilterScreenData = filterScreenData
    override fun actions(
        screenType: Any,
        navController: NavController
    ): com.example.russian.main.ui.actions.MyActions {
        return when (screenType) {
            is ScreenStats -> StatsScreenActions { search(it) }
            is ScreenFilters -> FilterScreenActions(
                onResetClick = {
                    val defaultData = defaultFilterSettings()
                    filterScreenData.playlistState.value =
                        PlaylistViewStateParent.PlaylistViewState(
                            filterScreenData.playlistState.value.playlistStates.map {
                                PlaylistState(it.title, true)
                            }
                        )
                    filterScreenData.answerState.value = SimpleBooleanState(
                        defaultData.showUnanswered,
                        "Показывать неотвеченные слова",
                        Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    )
                    filterScreenData.sortState.value = SortFilterViewState(
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
                    val playlists = filterScreenData.playlistState.value.playlistStates
                    filterScreenData.playlistState.value =
                        PlaylistViewStateParent.PlaylistViewState(
                            playlists.mapIndexed { index, playlist ->
                                val checked =
                                    if (ind == index) playlist.checked.not() else playlist.checked
                                PlaylistState(playlist.title, checked)
                            }
                        )

                },
                onSortTypeClick = { ind ->
                    val prev = filterScreenData.sortState.value.states

                    filterScreenData.sortState.value = SortFilterViewState(
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
                    val a = filterScreenData.answerState.value
                    filterScreenData.answerState.value =
                        SimpleBooleanState(a.show.not(), a.text, a.modifier)
                },
                onSaveClick = {

                    filterSettingFlow.value = FilterSettingData(
                        filterSettingFlow.value.playlists.mapIndexed { index, markedPlaylist ->
                            MarkedPlaylist(
                                markedPlaylist.playlist,
                                filterScreenData.playlistState.value.playlistStates[index].checked
                            )
                        },
                        filterSettingFlow.value.sortTypes.mapIndexed { index, sortType ->
                            SortType(
                                sortType.type,
                                filterScreenData.sortState.value.states[index].mode
                            )
                        },
                        filterScreenData.answerState.value.show
                    )
                }
            )

            //can never happen
            else -> throw RuntimeException("Unknown type of screen: ${screenType.javaClass.name}")
        }
    }

    override fun uiSettingsScreen(): SettingScreenData = settingsScreenData
    override fun actionsSettingsScreen(): SettingActions = settingsActions()

    private fun settingsActions() = SettingActions(
        onSoundClick = {
            settings.editor.putBoolean(settings.soundKey, it).apply()
            settings.soundOn = it
            settingsScreenData.soundState.checked.value = it
        },
        onVibrationClick = {
            settings.editor.putBoolean(settings.vibrationsKey, it).apply()
            settings.vibrationOn = it
            settingsScreenData.vibrationState.checked.value = it
        },
        onSliderChange = {
            settingsScreenData.testStatsCardState.winrate.value = it.toDouble()
        },
        onWinrateChangeClick = {
            settingsScreenData.testStatsCardState.showWinrate.value = it
            settings.showWinrate = it
            settings.editor.putBoolean(settings.winrateKey, it).apply()
        },
        onIconChangeClick = {
            settingsScreenData.testStatsCardState.showTypeIcon.value = it
            settings.showIcon = it
            settings.editor.putBoolean(settings.iconKey, it).apply()
        }, onIndicatorChangeClick = {
            settingsScreenData.testStatsCardState.showIndicator.value = it
            settings.showIndicator = it
            settings.editor.putBoolean(settings.indicatorKey, it).apply()
        }, onRatingClicked = {
            activityStarter.start(googleFormURI)
        }, onTelegramClick = {

        }
    )

    init {
        _pracRemoteUiState.value = PracScreenStage.Remote.Loading

        if (filterScreenData.playlistState.value is PlaylistViewStateParent.Loading) {


            //settings loading
            viewModelScope.launch {
                settings = DisplaySettings(
                    sharedPreferences = sharedPreferences
                )
                settingsScreenData = SettingScreenData(
                    vibrationState = SwitchState(
                        text = "Вибрация",
                        MutableStateFlow(settings.vibrationOn)
                    ),
                    soundState = SwitchState(text = "Звук", MutableStateFlow(settings.soundOn)),
                    testStatsCardState = TestStatsCardState(
                        winrate = MutableStateFlow(0.5),
                        showTypeIcon = MutableStateFlow(settings.showIcon),
                        showWinrate = MutableStateFlow(settings.showWinrate),
                        showIndicator = MutableStateFlow(settings.showIndicator)
                    )
                )
            }

            val filterDataFlow = repo.allPlaylistsFlow()
                .combine(filterSettingFlow) { playlists, filterSettingData ->

                    val positions = repo.getPlaylistPosition()
                    val positionMap = positions.associateBy({ it.playlistId }, { it.positionIndex })

                    _playlists = playlists.sortedBy {
                        positionMap[it.id] ?: (Int.MAX_VALUE - it.id.value.toInt())
                    }

                    _pracLocalUiState.value = PracScreenStage.Local.Data(_playlists)

                    val marked = mergeOldNewPlaylist(filterSettingData.playlists, playlists)
                    val filterData = FilterSettingData(
                        marked,
                        filterSettingData.sortTypes,
                        filterSettingData.showUnanswered
                    )

                    filterScreenData.playlistState.value =
                        PlaylistViewStateParent.PlaylistViewState(marked.map {
                            PlaylistState(
                                it.playlist.title,
                                it.marked
                            )
                        })
                    filterScreenData.sortState.value =
                        SortFilterViewState(filterSettingData.sortTypes.mapIndexed { index, it ->
                            SortFilterState(
                                getDisplayableName(it.type),
                                it.mode,
                                index == filterData.sortTypes.lastIndex
                            )
                        })
                    filterScreenData.answerState.value = SimpleBooleanState(
                        filterSettingData.showUnanswered,
                        filterScreenData.answerState.value.text,
                        filterScreenData.answerState.value.modifier
                    )

                    filterData
                }
                .flatMapMerge { filterData ->
                    filterSettingFlow.value = filterData
                    var allUnmarked = true
                    filterData.playlists.forEach {
                        if (it.marked) allUnmarked = false
                    }
                    if (allUnmarked) flowOf(emptyList())
                    else {
                        flowOf(emptyList<Statistics>())
                    }
                }

            combine(searchPrefFlow, filterDataFlow) { pref, words ->
                val filteredWords = words.filter {
                    it.displayableText.startsWith(pref)
                }
                if (filteredWords.isEmpty()) {
                    _uiGeneralState.value = StatsFirstScreenState.NothingFound {
                        search(it)
                    }
                } else {
                    val params = object : StatsParametersAPI {
                        override fun showWinRate(): Boolean {
                            return settings.showWinrate
                        }

                        override fun showWinRateIndicator(): Boolean {
                            return settings.showIndicator
                        }

                        override fun showIcon(): Boolean {
                            return settings.showIcon
                        }
                    }

                    _uiGeneralState.value = StatsFirstScreenState.Success(
                        WordMapper.wordListToCards(filteredWords, params),
                        Color.DarkGray,
                        onSearch = { prefix ->
                            search(prefix)
                        }
                    )
                }
            }
                .launchIn(viewModelScope)
        }
    }


    private fun search(pref: String) {
        viewModelScope.launch {
            searchPrefFlow.value = pref
        }
    }

    private fun onMovePlaylist(from: ItemPosition, to: ItemPosition) {
        _playlists = _playlists.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }

        _pracLocalUiState.value = PracScreenStage.Local.Data(_playlists)
        viewModelScope.launch(Dispatchers.IO) {
            repo.updatePlaylistPositions(_playlists)
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras
                val savedStateHandle = extras.createSavedStateHandle()

                val key = "Y4i_shared_preferences"
                val sharedPreferences = application.getSharedPreferences(key, Context.MODE_PRIVATE)
                val statsDao = (application as MyApplication).statsDao()

                return MainViewModel(
                    sharedPreferences, statsDao
                ) as T
            }
        }
    }
}

interface StatsParametersAPI {
    fun showWinRate(): Boolean
    fun showWinRateIndicator(): Boolean
    fun showIcon(): Boolean
}

private fun defaultFilterSettings() = FilterSettingData(
    emptyList(),
    listOf(
        SortType(SortTypesEnum.ALPHABETICAL, SortTypeMode.DIRECT),
        SortType(SortTypesEnum.WIN_RATE)
    ),
    true
)

data class DisplaySettings(
    val sharedPreferences: SharedPreferences,
    val editor: Editor = sharedPreferences.edit(),

    val indicatorKey: String = "indicator",
    var showIndicator: Boolean = sharedPreferences.getBoolean(indicatorKey, true),

    val winrateKey: String = "winrate",
    var showWinrate: Boolean = sharedPreferences.getBoolean(winrateKey, true),

    val iconKey: String = "show_icon",
    var showIcon: Boolean = sharedPreferences.getBoolean(iconKey, false),

    val soundKey: String = "sound",
    var soundOn: Boolean = sharedPreferences.getBoolean(soundKey, true),

    val vibrationsKey: String = "vibration",
    var vibrationOn: Boolean = sharedPreferences.getBoolean(vibrationsKey, true)
)

interface ActivityStarter {
    fun start(uri: String)
}