package com.example.russian.ui.state

import com.example.russian.back.data.entity.playlist.Playlist
import kotlinx.serialization.Serializable

sealed class PracScreenStage {
    data object Loading : PracScreenStage()

    sealed class Content(
        destination: PracDestination
    ): PracScreenStage(){

        class PracScreenLocal(
            var playlists: List<Playlist>
        ): Content(PracDestination.Local)

        class PracScreenRemote(

        ): Content(PracDestination.Remote)

    }
}
sealed class PracDestination{

    @Serializable
    data object Local: PracDestination()

    @Serializable
    data object Remote: PracDestination()
}