package com.example.remotelogin.wrappers


import com.example.russian.game.back.data.entity.playlist.Playlist
import com.example.russian.game.back.data.entity.playlist.PlaylistCrossRef
import com.example.russian.main.data.remote.PreviewTask
import com.example.russian.main.data.remote.RemotePlaylist
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class RequestResult {

    data object Success : RequestResult()

    @Serializable
    data class Error(
        val message: String
    ) : RequestResult()

    @Serializable
    sealed class Authentication : RequestResult() {

        @Serializable
        @SerialName("granted")
        data object Granted : Authentication()

        @Serializable
        @SerialName("denied")
        data class Denied(
            val message: String
        ) : Authentication()
    }

    @Serializable
    sealed class CreateAccount : RequestResult() {

        @Serializable
        @SerialName("created")
        data class Created(
            val token: String
        ) : CreateAccount()

        @Serializable
        @SerialName("error")
        data class Error(
            val errorMessage: String
        ) : CreateAccount()
    }

    @Serializable
    sealed class PlaylistPage {

        @Serializable
        @SerialName("playlist-page-data")
        data class Data(
            val playlists: List<RemotePlaylist>
        ) : PlaylistPage()

        @Serializable
        @SerialName("noting-more")
        data object NotingMore : PlaylistPage()

        @Serializable
        @SerialName("error")
        data class Error(val message: String) : PlaylistPage()

    }

    @Serializable
    sealed class RemoteTasks {

        @Serializable
        @SerialName("remote-tasks")
        data class Tasks(
            val data: List<PreviewTask>
        ) : RemoteTasks()


        data class Error(
            val massage: String
        ) : RemoteTasks()

    }

    @Serializable
    sealed class TaskDownload {

        @Serializable
        data class Data(
            val text: String
        ) : TaskDownload()

        data class Error(
            val message: String
        ) : TaskDownload()
    }
}