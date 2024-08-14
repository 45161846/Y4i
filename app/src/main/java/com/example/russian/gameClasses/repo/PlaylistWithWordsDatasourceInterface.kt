package com.example.russian.gameClasses.repo

import com.example.russian.architecture.data.olddata.dao.PlaylistWordCrossRefDao
import com.example.russian.architecture.data.olddata.entity.Playlist
import com.example.russian.architecture.data.olddata.entity.PlaylistWithWords
import com.example.russian.architecture.data.olddata.entity.WordWithSpellings
import kotlinx.coroutines.flow.Flow

interface PlaylistWithWordsDatasourceInterface {

    val playlistCrossRefDao: PlaylistWordCrossRefDao

    fun getPlaylistWithWords(playlistId: Int): Flow<PlaylistWithWords>

}