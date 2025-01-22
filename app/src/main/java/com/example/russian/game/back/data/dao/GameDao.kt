package com.example.russian.game.back.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.russian.main.Id
import com.example.russian.game.back.data.entity.PartOfTaskWithSpellingVariants
import com.example.russian.game.back.data.entity.Statistics
import com.example.russian.game.back.data.entity.TaskPartOfTask
import com.example.russian.game.back.data.entity.playlist.PlaylistWithWords
import com.example.russian.main.data.local.db.entity.Answer
import kotlinx.coroutines.flow.Flow


@Dao
interface GameDao {

    @Transaction
    @Query("SELECT * FROM `new-playlist` WHERE `playlistId` = :playlistId LIMIT 1")
    fun getPlaylistWithWords(playlistId: Id): Flow<PlaylistWithWords?>

    @Query("UPDATE Statistics SET `correct` = :correct, `attempts` = :attempts WHERE `taskId` = :taskId" )
    suspend fun updateStats(taskId: Id, correct: Int, attempts: Int)

    @Transaction
    @Query("SELECT * FROM MyTask WHERE `taskId` = :taskId LIMIT 1")
    fun getWordWithTask(taskId: Id) : Flow<TaskPartOfTask>

    @Query("SELECT * FROM STATISTICS WHERE `taskId` = :taskId LIMIT 1")
    suspend fun getStats(taskId: Id): Statistics

    @Transaction
    @Query("SELECT * FROM MyTask WHERE `taskId` = :taskId LIMIT 1")
    suspend fun getTaskWithParts(taskId: Id): TaskPartOfTask

    @Transaction
    @Query("SELECT * FROM PartOfTask WHERE `taskId` = :taskId")
    suspend fun getSpellingsAndParts(taskId: Id): List<PartOfTaskWithSpellingVariants>

    @Insert
    suspend fun addAnswer(answer: Answer)

}