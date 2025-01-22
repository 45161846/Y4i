package com.example.russian.main.prac

import com.example.russian.game.back.data.entity.playlist.Playlist
import com.example.russian.main.data.remote.RemotePlaylist
import com.example.russian.main.prac.remote.RemotePlaylistUiState
import com.example.russian.main.stats.comp.filter.MarkedPlaylist
import kotlinx.serialization.Serializable

sealed class PracScreenStage {

    sealed class Local: PracScreenStage(){
        data object Loading : Local()

        data class Data(
            var playlists: List<Playlist>
        ): Local()
    }

    sealed class Remote: PracScreenStage(){
        data object Loading : Remote()

        data class Data(
            val playlists: List<RemotePlaylist>
        ): Remote()
    }

}
sealed class PracDestination{

    @Serializable
    data object Local: PracDestination()

    @Serializable
    data object Remote: PracDestination()
}