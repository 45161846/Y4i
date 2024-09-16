package com.example.russian.repository.arch

import com.example.russian.back.data.dao.StatsDao
import com.example.russian.back.data.entity.Statistics
import com.example.russian.back.data.entity.playlist.Playlist
import com.example.russian.ui.state.FilterSettingData
import kotlinx.coroutines.flow.Flow

interface StatsScreenRepositoryInterface {

    fun setDao(statsDao: StatsDao)

    suspend fun wordsFiltered(filter: FilterSettingData): Flow<List<Statistics>>

    suspend fun allPlaylists(): List<Playlist>

    fun allPlaylistsFlow(): Flow<List<Playlist>>
}