package com.example.russian.game.back.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.russian.game.back.data.entity.Statistics
import com.example.russian.game.back.data.entity.playlist.Playlist
import com.example.russian.game.back.data.entity.playlist.PlaylistPositions
import com.example.russian.game.back.data.entity.playlist.PlaylistWithStats
import com.example.russian.game.back.data.entity.playlist.PlaylistWithWords
import com.example.russian.main.Id
import kotlinx.coroutines.flow.Flow


@Dao
interface StatsDao {

    @Transaction
    @Query("SELECT * FROM `new-playlist` WHERE playlistId = :playlistId")
    fun playlist(playlistId: Id): Flow<PlaylistWithWords>

    @Transaction
    @Query("SELECT * FROM STATISTICS WHERE `taskId` = :taskId")
    fun wordStats(taskId: Id): Flow<Statistics>

    @Query("SELECT * FROM STATISTICS")
    fun observeAllStats(): Flow<List<Statistics>>

    @Transaction
    @Query("SELECT * FROM `new-playlist` WHERE playlistId = :playlistId")
    fun playlistStats(playlistId: Id): Flow<PlaylistWithStats>

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

    @Query("UPDATE STATISTICS SET favorite = NOT favorite WHERE taskId = :taskId")
    suspend fun setFavorite(taskId: Id)
}