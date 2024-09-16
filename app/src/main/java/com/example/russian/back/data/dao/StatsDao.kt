package com.example.russian.back.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.russian.back.data.entity.NewWord
import com.example.russian.back.data.entity.WordStatistics
import com.example.russian.back.data.entity.playlist.Playlist
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

    fun wordsFromPlaylist(playlistId: Long): Flow<List<NewWord>>{
        return playlist(playlistId).map {
            it.words
        }
    }

    @Transaction
    @Query("SELECT * FROM newword WHERE `wordId` = :wordId")
    fun wordStats(wordId: Long): Flow<WordStatistics>

    fun wordsFromPlaylists(playlistIds: List<Long>): Flow<List<NewWord>> {

        var commonFlow = flowOf<List<NewWord>>()

        playlistIds.forEach {
            commonFlow = wordsFromPlaylist(it).combine(commonFlow)
            { a, b ->
                a + b
            }
        }

        return commonFlow
    }

    fun wordsStats(wordIds: List<Long>): Flow<List<WordStatistics>>{

        var commonFlow = flowOf<ArrayList<WordStatistics>>()

        wordIds.forEach {
            commonFlow = wordStats(it).combine(commonFlow)
            { a, b ->
                b.add(a)
                b
            }
        }

        return commonFlow
    }


    @Transaction
    @Query("SELECT * FROM `new-playlist` WHERE `playlistId` = :id")
    fun statsFromPlaylist(id: Long): Flow<PlaylistWithStats>

    @Query("SELECT * FROM `new-playlist`")
    suspend fun allPlaylists(): List<Playlist>

    @Query("SELECT * FROM `new-playlist`")
    fun allPlaylistsFlow(): Flow<List<Playlist>>
}