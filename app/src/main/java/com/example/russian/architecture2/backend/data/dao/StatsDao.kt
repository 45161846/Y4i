package com.example.russian.architecture2.backend.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.russian.architecture2.backend.data.entity.NewWord
import com.example.russian.architecture2.backend.data.entity.WordStatistics
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf


@Dao
interface StatsDao {

    @Transaction
    @Query("SELECT * FROM PLAYLISTCROSSREF WHERE playlistId = :playlistId")
    fun wordsFromPlaylist(playlistId: Long): Flow<List<NewWord>>

    @Transaction
    @Query("SELECT * FROM STATISTICS WHERE `wordId` = :wordId")
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
}