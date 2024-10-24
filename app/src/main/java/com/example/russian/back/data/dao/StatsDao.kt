package com.example.russian.back.data.dao

import android.icu.text.Transliterator.Position
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.russian.back.data.entity.MyTask
import com.example.russian.back.data.entity.WordStatistics
import com.example.russian.back.data.entity.playlist.Playlist
import com.example.russian.back.data.entity.playlist.PlaylistPositions
import com.example.russian.back.data.entity.playlist.PlaylistWithStats
import com.example.russian.back.data.entity.playlist.PlaylistWithWords
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map


@Dao
interface StatsDao {

    @Transaction
    @Query("SELECT * FROM `new-playlist` WHERE playlistId = :playlistId")
    fun playlist(playlistId: Long): Flow<PlaylistWithWords>

    fun wordsFromPlaylist(playlistId: Long): Flow<List<MyTask>>{
        return playlist(playlistId).map {
            it.words
        }
    }

    @Transaction
    @Query("SELECT * FROM MyTask WHERE `taskId` = :taskId")
    fun wordStats(taskId: Long): Flow<WordStatistics>

    fun wordsFromPlaylists(playlistIds: List<Long>): Flow<List<MyTask>> {

        var commonFlow = flowOf<List<MyTask>>()

        playlistIds.forEach {
            commonFlow = wordsFromPlaylist(it).combine(commonFlow)
            { a, b ->
                a + b
            }
        }

        return commonFlow
    }

    fun wordsStats(taskIds: List<Long>): Flow<List<WordStatistics>>{

        var commonFlow = flowOf<ArrayList<WordStatistics>>()

        taskIds.forEach {
            commonFlow = wordStats(it).combine(commonFlow)
            { a, b ->
                b.add(a)
                b
            }
        }

        return commonFlow
    }


    suspend fun updatePositionsOnScreen(playlists: List<Playlist>){
        updatePositionsInTable(
            playlists.mapIndexed{ind, pl ->
                PlaylistPositions(playlistId = pl.id, positionIndex = ind)
            }
        )
    }


    @Query("SELECT * FROM `playlistpositions`")
    suspend fun getPlaylistPosition(): List<PlaylistPositions>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePositionsInTable(positions: List<PlaylistPositions>)

    @Transaction
    @Query("SELECT * FROM `new-playlist` WHERE `playlistId` = :id")
    fun statsFromPlaylist(id: Long): Flow<PlaylistWithStats>

    @Query("SELECT * FROM `new-playlist`")
    suspend fun allPlaylists(): List<Playlist>

    @Query("SELECT * FROM `new-playlist`")
    fun allPlaylistsFlow(): Flow<List<Playlist>>
}