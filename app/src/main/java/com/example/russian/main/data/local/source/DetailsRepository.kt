package com.example.russian.main.data.local.source

import com.example.russian.game.back.data.entity.Statistics
import com.example.russian.game.back.data.entity.playlist.Playlist
import com.example.russian.game.back.data.entity.playlist.PlaylistUiApi
import com.example.russian.main.Id
import com.example.russian.main.TaskType
import com.example.russian.main.data.remote.RemotePlaylistRepository
import com.example.russian.main.prac.details.PlaylistContent
import com.example.russian.main.prac.details.PlaylistOverView
import com.example.russian.main.settings.SettingsHolder
import com.example.russian.main.util.toCardUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDetailsRepo @Inject constructor(
    private val playlistRepo: RemotePlaylistRepository,
    private val settingsHolder: SettingsHolder
) {

    val detailsUiState: MutableStateFlow<PlaylistOverView.Local> =
        MutableStateFlow(PlaylistOverView.Local.Loading)

    val content: MutableStateFlow<PlaylistContent.Local> =
        MutableStateFlow(PlaylistContent.Local.Loading(settingsHolder.displaySettingsFlow.value))


    fun open(playlist: Playlist) {

        content.value = PlaylistContent.Local.Loading(settingsHolder.displaySettingsFlow.value)

        detailsUiState.update {
            PlaylistOverView.Local.OverView(
                id = playlist.id,
                title = playlist.title,
                description = playlist.description,
                capacity = playlist.capacity.toInt()
            )
        }

    }

    fun updateContent(newContent: PlaylistContent.Local) {
        content.update { newContent }
    }
}