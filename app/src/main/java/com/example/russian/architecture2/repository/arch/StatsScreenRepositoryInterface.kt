package com.example.russian.architecture2.repository.arch

import com.example.russian.MyEnumClasses.MyFilterSettings
import com.example.russian.architecture2.backend.data.dao.StatsDao
import com.example.russian.architecture2.backend.data.entity.Statistics
import com.example.russian.architecture2.backend.data.entity.WordStatistics
import com.example.russian.architecture2.backend.data.entity.playlist.Playlist
import com.example.russian.architecture2.ui.state.FilterSettingData
import com.example.russian.architecture2.ui.state.FilterState
import kotlinx.coroutines.flow.Flow

interface StatsScreenRepositoryInterface {

    fun setDao(statsDao: StatsDao)

    suspend fun wordsFiltered(filter: FilterSettingData): Flow<List<Statistics>>

    suspend fun allPlaylists(): List<Playlist>

    fun allPlaylistsFlow(): Flow<List<Playlist>>
}