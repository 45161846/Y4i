package com.example.russian.main.prac

import com.example.russian.game.back.data.entity.playlist.Playlist
import com.example.russian.main.data.remote.RemotePlaylist
import kotlinx.serialization.Serializable

sealed class PracScreenStage {

    sealed class Local : PracScreenStage() {
        data object Loading : Local()

        data class Data(
            var playlists: List<Playlist>
        ) : Local()
    }

    data class Remote(
        val playlists: List<RemotePlaylistUi>,
        val lastLoadedIndex: Int
    ) : PracScreenStage()
}

sealed class RemotePlaylistUi{
    data object Loading: RemotePlaylistUi()

    data class Data(
        val playlist: RemotePlaylist
    ): RemotePlaylistUi()

    sealed class EndCard: RemotePlaylistUi(){

        data object NothingMore: EndCard()
        data class Error(
            val message: String
        ): EndCard()

    }
}

sealed class PracDestination{

    @Serializable
    data object Local: PracDestination()

    @Serializable
    data object Remote: PracDestination()
}