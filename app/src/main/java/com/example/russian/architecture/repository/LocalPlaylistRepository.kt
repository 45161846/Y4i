package com.example.russian.architecture.repository

import com.example.russian.architecture.data.olddata.entity.Playlist
import com.example.russian.architecture.data.olddata.dao.PlaylistDao
import kotlinx.coroutines.flow.Flow

interface LocalPlaylistRepository {

    val playlistDao: PlaylistDao

    fun getAll(): Flow<List<Playlist>>

    fun getOne(id: Int): Flow<Playlist>
    fun getWithPrefix(pref: String): Flow<List<Playlist>>

    suspend fun changeCapacity(id: Int, newValue: Int)
    suspend fun changeCapacity(title: String, newValue: Int)

    suspend fun remove(id: Int)
    suspend fun remove(title: String)
}