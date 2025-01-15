package com.example.russian.main.repository.arch

import com.example.russian.architectured.stats.comp.filter.FilterState
import com.example.russian.main.back.data.entity.Statistics
import com.example.russian.main.back.data.entity.playlist.Playlist
import com.example.russian.main.back.data.entity.playlist.PlaylistPositions
import com.example.russian.main.ui.state.FilterSettingData
import kotlinx.coroutines.flow.Flow

interface StatsScreenRepositoryInterface {

    suspend fun wordsFiltered(filter: FilterState): Flow<List<Statistics>>

    suspend fun allPlaylists(): List<Playlist>

    fun allPlaylistsFlow(): Flow<List<Playlist>>

    suspend fun updatePlaylistPositions(playlists: List<Playlist>)

    suspend fun getPlaylistPosition(): List<PlaylistPositions>

    fun getPlaylistPositionFlow(): Flow<List<PlaylistPositions>>
}