package com.example.russian.main.back.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.russian.architectured.Id
import com.example.russian.main.back.data.entity.MyTask
import com.example.russian.main.back.data.entity.Statistics
import com.example.russian.main.back.data.entity.WordStatistics
import com.example.russian.main.back.data.entity.playlist.Playlist
import com.example.russian.main.back.data.entity.playlist.PlaylistPositions
import com.example.russian.main.back.data.entity.playlist.PlaylistWithStats
import com.example.russian.main.back.data.entity.playlist.PlaylistWithWords
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map


@Dao
interface StatsDao {

    @Transaction
    @Query("SELECT * FROM `new-playlist` WHERE playlistId = :playlistId")
    fun playlist(playlistId: Id): Flow<PlaylistWithWords>

    @Transaction
    @Query("SELECT * FROM MyTask WHERE `taskId` = :taskId")
    fun wordStats(taskId: Id): Flow<WordStatistics>

    @Query("SELECT * FROM STATISTICS")
    fun observeAllStats(): Flow<List<Statistics>>


    suspend fun updatePositionsOnScreen(playlists: List<Playlist>){
        updatePositionsInTable(
            playlists.mapIndexed{ind, pl ->
                PlaylistPositions(playlistId = pl.id, positionIndex = ind)
            }
        )
    }


    @Query("SELECT * FROM `playlistpositions`")
    suspend fun getPlaylistPosition(): List<PlaylistPositions>

    @Query("SELECT * FROM `playlistpositions`")
    fun getPlaylistPositionFlow(): Flow<List<PlaylistPositions>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePositionsInTable(positions: List<PlaylistPositions>)

    @Transaction
    @Query("SELECT * FROM `new-playlist` WHERE `playlistId` = :id")
    fun statsFromPlaylist(id: Id): Flow<PlaylistWithStats>

    @Query("SELECT * FROM `new-playlist`")
    suspend fun allPlaylists(): List<Playlist>

    @Query("SELECT * FROM `new-playlist`")
    fun allPlaylistsFlow(): Flow<List<Playlist>>


    @Query("SELECT playlistId FROM PLAYLISTCROSSREF WHERE taskId = :taskId")
    suspend fun allPlaylistIdContainTask(taskId: Id): List<Id>
}