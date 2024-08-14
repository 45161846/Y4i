package com.example.russian.architecture.repository

import com.example.russian.architecture.data.olddata.entity.Playlist
import com.example.russian.architecture.data.olddata.dao.PlaylistDao
import kotlinx.coroutines.flow.Flow

class LocalPlaylistRepositoryImpl(
    override val playlistDao: PlaylistDao
): LocalPlaylistRepository {

    override fun getAll(): Flow<List<Playlist>>  = playlistDao.getAll()


    override fun getOne(id: Int): Flow<Playlist> = playlistDao.getPlaylistById(id)


    override fun getWithPrefix(pref: String): Flow<List<Playlist>>  = playlistDao.getWithPrefix(pref)

    override suspend fun changeCapacity(id: Int, newValue: Int){
        playlistDao.updateCapacity(id = id, newCapacity = newValue)
    }

    override suspend fun changeCapacity(title: String, newValue: Int) {
        playlistDao.updateCapacity(title = title, newCapacity = newValue)
    }

    override suspend fun remove(id: Int) {
        playlistDao.remove(id)
    }

    override suspend fun remove(title: String) {
        playlistDao.remove(title)
    }
}