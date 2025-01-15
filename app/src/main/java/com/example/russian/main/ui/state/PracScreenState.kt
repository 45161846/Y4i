package com.example.russian.main.ui.state

import com.example.russian.main.back.data.entity.playlist.Playlist
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
    }

}
sealed class PracDestination{

    @Serializable
    data object Local: PracDestination()

    @Serializable
    data object Remote: PracDestination()
}