package com.example.russian.game.repository.arch

import com.example.russian.game.back.data.entity.Statistics
import com.example.russian.game.back.data.entity.playlist.Playlist
import com.example.russian.game.back.data.entity.playlist.PlaylistPositions
import com.example.russian.main.Id
import kotlinx.coroutines.flow.Flow

interface StatsScreenRepositoryInterface {

    fun allPlaylistsFlow(): Flow<List<Playlist>>

    suspend fun updatePlaylistPositions(playlists: List<Playlist>)

    suspend fun getPlaylistPosition(): List<PlaylistPositions>

    fun playlistTasks(playlistId: Id): Flow<List<Statistics>>
}