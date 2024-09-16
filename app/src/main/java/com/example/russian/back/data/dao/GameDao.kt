package com.example.russian.back.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.russian.back.data.entity.Statistics
import com.example.russian.back.data.entity.WordTaskSpelling
import com.example.russian.back.data.entity.playlist.PlaylistWithWords
import kotlinx.coroutines.flow.Flow


@Dao
interface GameDao {

    @Transaction
    @Query("SELECT * FROM `new-playlist` WHERE `playlistId` = :playlistId LIMIT 1")
    fun getPlaylistWithWords(playlistId: Long): Flow<PlaylistWithWords?>

    @Query("UPDATE Statistics SET `correct` = :correct, `attempts` = :attempts WHERE `wordId` = :wordID" )
    suspend fun updateStats(wordID: Long, correct: Int, attempts: Int)

    @Transaction
    @Query("SELECT * FROM NewWord WHERE `wordId` = :wordId LIMIT 1")
    fun getWordWithTask(wordId: Long) : Flow<WordTaskSpelling>

    @Query("SELECT * FROM STATISTICS WHERE `wordId` = :wordId LIMIT 1")
    suspend fun getStats(wordId: Long): Statistics

}