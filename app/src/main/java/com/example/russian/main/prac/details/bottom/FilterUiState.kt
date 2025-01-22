package com.example.russian.main.prac.details.bottom

import com.example.russian.game.back.data.entity.Statistics
import com.example.russian.main.Id
import com.example.russian.main.stats.comp.filter.FilterScreenActions
import com.example.russian.main.stats.comp.filter.FilterState
import com.example.russian.main.stats.comp.filter.MarkedPlaylist
import com.example.russian.main.stats.comp.filter.MarkedPlaylistVariant
import com.example.russian.main.stats.comp.filter.PercentageBounds
import com.example.russian.main.stats.comp.filter.SortDirection
import com.example.russian.main.stats.comp.filter.SortType
import com.example.russian.main.stats.comp.filter.reverse
import com.example.russian.main.util.and
import com.example.russian.main.util.update
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

abstract class FilteredStatsUiState(
    source: Flow<List<Statistics>>,
    coroutineScope: CoroutineScope
) {

    private val markedPlaylists: MutableStateFlow<MarkedPlaylistVariant> =
        MutableStateFlow(MarkedPlaylistVariant.All)

    fun changeMarkedPlaylists(playlists: MarkedPlaylistVariant){
        markedPlaylists.update { playlists }
    }

    abstract suspend fun getPlaylistByTaskId(taskId: Id): List<Id>

    private val sortType: MutableStateFlow<SortType> = MutableStateFlow(
        SortType.ALPHABETICAL(SortDirection.UP)
    )
    private val showUnanswered: MutableStateFlow<Boolean> = MutableStateFlow(
        true
    )
    private val bounds: MutableStateFlow<PercentageBounds> = MutableStateFlow(
        PercentageBounds(0, 100)
    )
    private val searchPref = MutableStateFlow("")


    val filterNoPlaylists: StateFlow<FilterState> =
        combine(sortType, showUnanswered, bounds, markedPlaylists)
        { sortType, show, bounds, playlists ->

            FilterState(
                markedPlaylists = playlists,
                sortType = sortType,
                showUnanswered = show,
                bounds = bounds
            )
        }
            .stateIn(
                coroutineScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = FilterState.default
            )


    private val filterState = MutableStateFlow(
        FilterState.default
    )


    val stats = combine(source, filterState, markedPlaylists) { stats, filter, playlists ->
        val minWR = filter.bounds.minValue.toDouble() / 100
        val maxWR = filter.bounds.maxValue.toDouble() / 100

        val filtered = stats.filter { stat ->
            val b1 = (filter.showUnanswered || stat.attempts > 0)
            val b2 = when (playlists) {
                is MarkedPlaylistVariant.All -> true
                is MarkedPlaylistVariant.Partial -> {
                    and(
                        getPlaylistByTaskId(stat.taskId),
                        playlists.markedPlaylists
                            .filter {
                                it.marked
                            }
                            .map {
                                it.playlist.id
                            }
                    )
                }
            }
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
    }.combine(searchPref) { filtered, pref ->
        filtered.filter {
            it.displayableText.startsWith(pref)
        }
    }

    private fun resetFilter() {
        resetPlaylists()

        bounds.update {
            PercentageBounds(0, 100)
        }
        sortType.update {
            SortType.ALPHABETICAL(SortDirection.UP)
        }
        showUnanswered.value = true
    }

    private fun onPlaylistClick(markedPlaylist: MarkedPlaylist) {
        markedPlaylists.update {
            when (it) {
                is MarkedPlaylistVariant.All -> it
                is MarkedPlaylistVariant.Partial -> {
                    MarkedPlaylistVariant.Partial(
                        it.markedPlaylists.update(markedPlaylist)
                    )
                }
            }
        }
    }

    private fun resetPlaylists() {
        markedPlaylists.update {
            when (it) {
                is MarkedPlaylistVariant.All -> it
                is MarkedPlaylistVariant.Partial -> MarkedPlaylistVariant.Partial(
                    it.markedPlaylists.map { playlist ->
                        MarkedPlaylist(playlist.playlist, true)
                    }
                )
            }

        }
    }

    private fun onSortClick(type: SortType) {
        sortType.update {
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
        showUnanswered.update {
            it.not()
        }
    }

    val actions = FilterScreenActions(
        onResetClick = ::resetFilter,
        onPlaylistClick = ::onPlaylistClick,
        onSortTypeClick = ::onSortClick,
        onShowUnansweredClick = ::onUnansweredClick,
        onSaveClick = {
            searchPref.value = ""
            filterState.value = filterNoPlaylists.value
        }
    )

    fun search(pref: String) {
        searchPref.value = pref
    }

}