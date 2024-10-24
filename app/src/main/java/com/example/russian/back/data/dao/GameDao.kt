package com.example.russian.back.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.russian.back.data.entity.PartOfTaskWithSpellingVariants
import com.example.russian.back.data.entity.Statistics
import com.example.russian.back.data.entity.TaskPartOfTask
import com.example.russian.back.data.entity.playlist.PlaylistWithWords
import kotlinx.coroutines.flow.Flow


@Dao
interface GameDao {

    @Transaction
    @Query("SELECT * FROM `new-playlist` WHERE `playlistId` = :playlistId LIMIT 1")
    fun getPlaylistWithWords(playlistId: Long): Flow<PlaylistWithWords?>

    @Query("UPDATE Statistics SET `correct` = :correct, `attempts` = :attempts WHERE `taskId` = :taskId" )
    suspend fun updateStats(taskId: Long, correct: Int, attempts: Int)

    @Transaction
    @Query("SELECT * FROM MyTask WHERE `taskId` = :taskId LIMIT 1")
    fun getWordWithTask(taskId: Long) : Flow<TaskPartOfTask>

    @Query("SELECT * FROM STATISTICS WHERE `taskId` = :taskId LIMIT 1")
    suspend fun getStats(taskId: Long): Statistics

    @Transaction
    @Query("SELECT * FROM MyTask WHERE `taskId` = :taskId LIMIT 1")
    suspend fun getTaskWithParts(taskId: Long): TaskPartOfTask

    @Transaction
    @Query("SELECT * FROM PartOfTask WHERE `taskId` = :taskId")
    suspend fun getSpellingsAndParts(taskId: Long): List<PartOfTaskWithSpellingVariants>

}