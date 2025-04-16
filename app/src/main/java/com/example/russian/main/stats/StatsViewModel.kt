package com.example.russian.main.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.russian.main.Id
import com.example.russian.main.data.local.db.repo.StatsRepositoryApi
import com.example.russian.main.prac.details.bottom.FilteredStatsUiState
import com.example.russian.main.settings.SettingsHolder
import com.example.russian.main.stats.comp.filter.MarkedPlaylist
import com.example.russian.main.stats.comp.filter.MarkedPlaylistVariant
import com.example.russian.main.stats.comp.stats.StatsScreenState
import com.example.russian.main.util.toCardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val repo: StatsRepositoryApi,
    settingsHolder: SettingsHolder
) : ViewModel() {

    private val _displayParams = settingsHolder.displaySettingsFlow

    private val filteredStatsState = object : FilteredStatsUiState(
        repo.allStats(),
        viewModelScope
    ) {

        override suspend fun getPlaylistByTaskId(taskId: Id): List<Id> {
            return repo.allPlaylistIdContainTask(taskId)
        }
    }

    val filterUiState = filteredStatsState.filterNoPlaylists

    init {
        viewModelScope.launch {
            repo.allPlaylists().collect {
                filteredStatsState.changeMarkedPlaylists(
                    MarkedPlaylistVariant.Partial(
                        it.map { playlist ->
                            MarkedPlaylist(playlist, true)
                        }
                    )
                )
            }
        }
    }


    val uiState: StateFlow<StatsScreenState> =
        combine(filteredStatsState.stats, _displayParams) { stats, params ->

            StatsScreenState.UI(
                stats.map {
                    it.toCardUiState(params)
                }
            )

        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = StatsScreenState.Loading(settingsHolder.displaySettingsFlow.value)
            )

    fun search(pref: String) {
        filteredStatsState.search(pref)
    }

    fun addToFavorite(taskId: Id){
        viewModelScope.launch {
            repo.setFavorite(taskId)
        }
    }

    val actions = filteredStatsState.actions
}