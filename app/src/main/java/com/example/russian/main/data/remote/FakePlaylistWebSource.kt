package com.example.russian.main.data.remote

import com.example.remotelogin.WebApi
import com.example.remotelogin.wrappers.RequestResult
import com.example.russian.main.Id
import com.example.russian.main.prac.remote.testRemotePlaylistState
import com.example.russian.main.util.getRandomString
import kotlinx.coroutines.delay
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class FakePlaylistWebSource @Inject constructor(

) : WebApi(), PlaylistWebSourceAPI {
    override suspend fun getPreviewTasks(
        remotePlaylistId: Id,
        after: Int,
        amount: Int
    ): RequestResult.RemoteTasks {

        delay(1000L)

        return RequestResult.RemoteTasks.Tasks(
            List(100) {
                PreviewTask(
                    Id(it.toLong()),
                    getRandomString(Random.nextInt(5, 10))
                )
            }
        )
    }

    override suspend fun getPlaylists(after: Int, amount: Int): RequestResult.PlaylistPage {

        delay(1000L)

        if (after > 20) {
            return RequestResult.PlaylistPage.NotingMore
        }

        val state1 = RemotePlaylist(
            remoteId = Id(1231),
            title = "Топ-50 ошибок 2025/2024",
            description = "Скорее забирай набор главных ловушек ФИПИ. 90% не сдаст из-за них",
            rating = 2.7F,
            capacity = 49,
            listOf("на..бегу", "балОвать???", "впр..прыжку", "движ..мый")
        )

        val state2 = RemotePlaylist(
            remoteId = Id(123412),
            title = "Ни за что не угадаешь, что поджидает тебя в 28 задании",
            description = "Набор эксклюзивных сливов!!! Только у нас всего за 27 тугриков ты сможешь получить ВСЕ ДЕМО ФИПИ",
            rating = 1.2F,
            capacity = 13,
            listOf("Эксклюзив №1", "Ого, тут что-то стоящее")
        )

        return RequestResult.PlaylistPage.Data(
            listOf(
                state1, state2, state2, state1, state2
            )
        )
    }

    override suspend fun loadTask(taskId: Id): RequestResult.TaskDownload {
        TODO("Not yet implemented")
    }

}