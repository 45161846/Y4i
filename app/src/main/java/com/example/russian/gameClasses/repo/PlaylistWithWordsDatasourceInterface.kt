package com.example.russian.gameClasses.repo

import com.example.russian.architecture.data.olddata.dao.PlaylistWordCrossRefDao
import com.example.russian.architecture.data.olddata.entity.PlaylistWithWords
import kotlinx.coroutines.flow.Flow

interface PlaylistWithWordsDatasourceInterface {

    val playlistCrossRefDao: PlaylistWordCrossRefDao

    fun getPlaylistWithWords(playlistId: Int): Flow<PlaylistWithWords>

}