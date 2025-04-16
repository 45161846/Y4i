package com.example.russian.main.data.remote

import com.example.remotelogin.WebApi
import com.example.remotelogin.wrappers.RequestResult
import com.example.russian.main.Id
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import javax.inject.Inject
import javax.inject.Singleton


const val REQUEST_ERROR = "Request error"

@Singleton
class PlaylistWebSource @Inject constructor(

) : WebApi(), PlaylistWebSourceAPI {

    override suspend fun getPreviewTasks(remotePlaylistId: Id, after: Int, amount: Int): RequestResult.RemoteTasks {
        // TODO
        // как будто after и amount абсолютно бесполезны и не сильно дороже все превью слова сразу грузить
        try {
            val getter = client.get {
                url("$BASE_URL/playlist/${remotePlaylistId.value}")
                setAttributes {
                    parameter("after", after)
                    parameter("amount", amount)
                }
            }
            val result: RequestResult.RemoteTasks.Tasks = getter
                .body()
            return result

        } catch (e: Exception) {
            return RequestResult.RemoteTasks.Error(e.message ?: "Пустая ошибка")
        }
    }

    override suspend fun getPlaylists(after: Int, amount: Int): RequestResult.PlaylistPage {

        try {
            val getter = client.get {
                url("$BASE_URL/playlist")
                setAttributes {
                    parameter("after", after)
                    parameter("amount", amount)
                }
            }
            val result: RequestResult.PlaylistPage = getter
                .body()
            return result

        } catch (e: Exception) {
            return RequestResult.PlaylistPage.Error(e.message ?: REQUEST_ERROR)
        }
    }


    /**
     * @return string value of playlist file
     */
    override suspend fun loadTask(
        taskId: Id
    ): RequestResult.TaskDownload {
        try {
            val getter = client.get {
                url("$BASE_URL/download/task")
                setAttributes {
                    parameter("id", taskId.value)
                }
            }

            val result: RequestResult.TaskDownload.Data = getter.body()
            return result
        } catch (e: Exception) {
            return RequestResult.TaskDownload.Error(e.message ?: "empty error message")
        }
    }
}

interface PlaylistWebSourceAPI {
    suspend fun getPreviewTasks(remotePlaylistId: Id, after: Int, amount: Int): RequestResult.RemoteTasks
    suspend fun getPlaylists(after: Int, amount: Int): RequestResult.PlaylistPage
    suspend fun loadTask(
        taskId: Id
    ) : RequestResult.TaskDownload
}