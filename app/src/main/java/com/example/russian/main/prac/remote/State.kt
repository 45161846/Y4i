package com.example.russian.main.prac.remote

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.russian.main.Id
import com.example.russian.main.data.remote.RemotePlaylist
import com.example.russian.main.prac.PracScreenStage
import com.example.russian.main.prac.RemotePlaylistUi


data class RemotePlaylistUiState(
    val title: String,
    val description: String,
    val backColor: PlaylistColor,
    val previewTasks: List<String>,
    val rating: Float,
    val taskCount: Int,
)

sealed class PlaylistColor{
    data object Regular: PlaylistColor()

    data class Special(
        val topBarColor: Color,
        val textColor: Color
    ): PlaylistColor()
}


fun testRemotePlaylistState(): PracScreenStage.Remote{

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

    return PracScreenStage.Remote(
        listOf(state1, state2).map{
            RemotePlaylistUi.Data(it)
        },
        2
    )

}