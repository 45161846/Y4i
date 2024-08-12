package com.example.russian.gameClasses.repo

import com.example.russian.architecture.data.dao.PlaylistWordCrossRefDao
import com.example.russian.architecture.data.entity.Playlist
import com.example.russian.architecture.data.entity.PlaylistWithWords
import kotlinx.coroutines.flow.Flow

class PlaylistWithWordsDatasource(
    override val playlistCrossRefDao: PlaylistWordCrossRefDao,
): PlaylistWithWordsDatasourceInterface {

    override fun getPlaylistWithWords(playlistId: Int): Flow<PlaylistWithWords> {
        return playlistCrossRefDao.getPlaylistWithWords(playlistId)
    }
}