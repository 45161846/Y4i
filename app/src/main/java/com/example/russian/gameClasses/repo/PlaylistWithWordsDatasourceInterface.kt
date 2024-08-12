package com.example.russian.gameClasses.repo

import com.example.russian.architecture.data.dao.PlaylistWordCrossRefDao
import com.example.russian.architecture.data.entity.Playlist
import com.example.russian.architecture.data.entity.PlaylistWithWords
import com.example.russian.architecture.data.entity.WordWithSpellings
import kotlinx.coroutines.flow.Flow

interface PlaylistWithWordsDatasourceInterface {

    val playlistCrossRefDao: PlaylistWordCrossRefDao

    fun getPlaylistWithWords(playlistId: Int): Flow<PlaylistWithWords>

}