package com.example.russian.main.data.remote

import com.example.remotelogin.WebApi
import com.example.russian.main.Id
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PlaylistWebSource @Inject constructor(

): WebApi() {

    suspend fun getTasks(remotePlaylistId: Id, after: Int, amount: Int): List<String>{
        //TODO
        //add real tasks call
        delay(1000)
        return listOf(
            "skudbakjs", "askdbasdb", "saldnasjd", "asdbaj,sdna"
        )
    }

    suspend fun getPlaylists(after: Int, amount: Int): List<RemotePlaylist>{
        //TODO
        //add real playlist call

        val playlists = mutableListOf<RemotePlaylist>()

        for(i in 1..amount){
             playlists.add(
                RemotePlaylist(
                    remoteId = Id(1231 + (i*10).toLong()),
                    title = "$i Топ-50 ошибок 2025/2024",
                    description = "Скорее забирай набор главных ловушек ФИПИ. 90% не сдаст из-за них",
                    rating = 2.7F,
                    capacity = 49,
                    listOf("на..бегу", "балОвать???", "впр..прыжку", "движ..мый")
                )
            )
        }

        delay(2000)

        return playlists
    }
}