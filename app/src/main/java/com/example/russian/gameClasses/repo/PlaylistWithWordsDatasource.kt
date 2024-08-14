package com.example.russian.gameClasses.repo

import com.example.russian.architecture.data.olddata.dao.PlaylistWordCrossRefDao
import com.example.russian.architecture.data.olddata.entity.Playlist
import com.example.russian.architecture.data.olddata.entity.PlaylistWithWords
import kotlinx.coroutines.flow.Flow

class PlaylistWithWordsDatasource(
    override val playlistCrossRefDao: PlaylistWordCrossRefDao,
): PlaylistWithWordsDatasourceInterface {

    override fun getPlaylistWithWords(playlistId: Int): Flow<PlaylistWithWords> {
        return playlistCrossRefDao.getPlaylistWithWords(playlistId)
    }
}