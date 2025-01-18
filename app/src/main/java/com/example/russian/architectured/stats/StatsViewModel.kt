package com.example.russian.architectured.stats

import androidx.compose.ui.util.fastMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.russian.R
import com.example.russian.architectured.TaskType
import com.example.russian.architectured.data.local.db.repo.StatsRepositoryApi
import com.example.russian.architectured.settings.SettingsHolder
import com.example.russian.architectured.stats.comp.filter.FilterScreenActions
import com.example.russian.architectured.stats.comp.filter.FilterState
import com.example.russian.architectured.stats.comp.filter.PercentageBounds
import com.example.russian.architectured.stats.comp.filter.SortDirection
import com.example.russian.architectured.stats.comp.filter.SortType
import com.example.russian.architectured.stats.comp.filter.reverse
import com.example.russian.architectured.stats.comp.stats.CardUIData
import com.example.russian.architectured.stats.comp.stats.StatsScreenState
import com.example.russian.architectured.util.and
import com.example.russian.architectured.util.join
import com.example.russian.architectured.util.mutableStateIn
import com.example.russian.architectured.util.update
import com.example.russian.main.ui.state.MarkedPlaylist
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val repo: StatsRepositoryApi,
    settingsHolder: SettingsHolder
) : ViewModel() {


    private val _stats = repo.allStats().mutableStateIn(
        viewModelScope, emptyList()
    )
    private val _displayParams = settingsHolder.displaySettingsFlow

    private val _markedPlaylists: MutableStateFlow<List<MarkedPlaylist>> =
        MutableStateFlow(emptyList())

    private val _sortType: MutableStateFlow<SortType> = MutableStateFlow(
        SortType.ALPHABETICAL(SortDirection.UP)
    )
    private val _showUnanswered: MutableStateFlow<Boolean> = MutableStateFlow(
        true
    )
    private val _bounds: MutableStateFlow<PercentageBounds> = MutableStateFlow(
        PercentageBounds(0, 100)
    )

    init {
        viewModelScope.launch {
            repo.allPlaylists().collect {
                _markedPlaylists.update { marked ->
                    join(it, marked)
                }
            }
        }
    }


    val filterState: StateFlow<FilterState> =
        combine(_markedPlaylists, _sortType, _showUnanswered, _bounds)
        { markedPlaylists, sortType, show, bounds ->

            FilterState(
                markedPlaylists = markedPlaylists,
                sortType = sortType,
                showUnanswered = show,
                bounds = bounds
            )
        }
            .stateIn(
                scope = viewModelScope,
                SharingStarted.WhileSubscribed(5000L),
                FilterState(
                    markedPlaylists = emptyList(),
                    sortType = SortType.BY_WIN_RATE(SortDirection.UP),
                    showUnanswered = true,
                    bounds = PercentageBounds(0, 100)
                )
            )

    val uiState: StateFlow<StatsScreenState> = combine(_stats, _displayParams) { stats, params ->
        StatsScreenState.UI(
            stats.fastMap {
                CardUIData(
                    text = it.displayableText,
                    winRate = it.correct.toDouble() / (it.attempts).toDouble(),
                    hasBeenAnswered = it.attempts > 0,
                    themeIconId = iconId(it.type),
                    params = params
                )
            }
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = StatsScreenState.Loading
        )

    fun search(pref: String) {
        _stats.update {
            repo.filteredStats.filter { stat ->
                stat.displayableText.startsWith(pref)
            }
        }
    }

    private fun resetFilter() {
        _markedPlaylists.update {
            it.map { playlist ->
                MarkedPlaylist(playlist.playlist, true)
            }
        }
        _bounds.update {
            PercentageBounds(0, 100)
        }
        _sortType.update {
            SortType.ALPHABETICAL(SortDirection.UP)
        }
        _showUnanswered.value = true
    }

    private fun onPlaylistClick(markedPlaylist: MarkedPlaylist) {
        _markedPlaylists.update {
            it.update(markedPlaylist)
        }
    }

    private fun onSortClick(type: SortType) {
        _sortType.update {
            when (type) {
                is SortType.BY_WIN_RATE -> {
                    if (it == type) type.copy(direction = it.direction.reverse()) else type.copy()
                }

                is SortType.ALPHABETICAL -> {
                    if (it == type) type.copy(direction = it.direction.reverse()) else type.copy()
                }
            }
        }
    }

    private fun onUnansweredClick() {
        _showUnanswered.update {
            it.not()
        }
    }

    val actions = FilterScreenActions(
        onResetClick = ::resetFilter,
        onPlaylistClick = ::onPlaylistClick,
        onSortTypeClick = ::onSortClick,
        onShowUnansweredClick = ::onUnansweredClick,
    ) {
        viewModelScope.launch {
            filterState.value.let { filter ->
                _stats.update {
                    val minWR = filter.bounds.minValue.toDouble() / 100
                    val maxWR = filter.bounds.maxValue.toDouble() / 100

                    val ids = filter.markedPlaylists.filter { it.marked }.map { it.playlist.id }

                    val filtered = repo.cashedStats.filter { stat ->
                        val b1 = (filter.showUnanswered || stat.attempts > 0)
                        val b2 = and(repo.allPlaylistIdContainTask(stat.taskId), ids)
                        val b3 = stat.winRate() in minWR..maxWR
                        b1 && b2 && b3
                    }
                    val sorted = if (filter.sortType is SortType.ALPHABETICAL) {
                        filtered.sortedBy {
                            it.displayableText
                        }
                    } else {
                        filtered.sortedBy {
                            it.winRate()
                        }
                    }

                    if (filter.sortType.direction == SortDirection.DOWN) sorted.reversed() else sorted

                }
            }
        }


    }


    private fun iconId(taskType: TaskType): Int {
        return when (taskType) {
            TaskType.YDARENIA -> R.drawable.ydar_icon
            else -> R.drawable.paromins_icon
        }
    }

}