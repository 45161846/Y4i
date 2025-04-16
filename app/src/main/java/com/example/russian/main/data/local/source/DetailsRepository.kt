package com.example.russian.main.data.local.source

import com.example.russian.game.back.data.entity.playlist.Playlist
import com.example.russian.main.prac.details.PlaylistContent
import com.example.russian.main.prac.details.PlaylistOverView
import com.example.russian.main.prac.details.bottom.BottomFilterActions
import com.example.russian.main.prac.details.bottom.BottomFilterState
import com.example.russian.main.settings.SettingsHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDetailsRepo @Inject constructor(
    private val settingsHolder: SettingsHolder
) {

    val detailsUiState: MutableStateFlow<PlaylistOverView.Local> =
        MutableStateFlow(PlaylistOverView.Local.Loading)

    val content: MutableStateFlow<PlaylistContent.Local> =
        MutableStateFlow(PlaylistContent.Local.Loading(settingsHolder.displaySettingsFlow.value))

    val filterState: MutableStateFlow<BottomFilterState> =
        MutableStateFlow(BottomFilterState.default())
    val filterActions = BottomFilterActions(
        onSortTypeClick = {
            filterState.update { filter ->
                filter.copy(
                    sortType = it
                )
            }
        },
        onShowUnanswered = {
            filterState.update { filter ->
                filter.copy(
                    showUnanswered = filter.showUnanswered.not()
                )
            }
        },
        onBoundsChange = {
            filterState.update { filter ->
                filter.copy(
                    bounds = it
                )
            }
        }
    )

    fun open(playlist: Playlist) {

        content.value = PlaylistContent.Local.Loading(settingsHolder.displaySettingsFlow.value)

        detailsUiState.update {
            PlaylistOverView.Local.OverView(
                id = playlist.id,
                title = playlist.title,
                description = playlist.description,
                capacity = playlist.capacity
            )
        }

    }

    fun updateContent(newContent: PlaylistContent.Local) {
        content.update { newContent }
    }
}