package com.example.russian.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WordDao {

    @Insert
    suspend fun insert(word: Word)

    @Insert
    suspend fun insert(words: List<Word>)

    @Query("SELECT * FROM word")
    suspend fun getAllWords(): List<Word>

    @Query("SELECT * FROM word WHERE `id` = :requested_id LIMIT 1")
    suspend fun getWordById(requested_id: Int): Word

    @Query("SELECT * FROM word WHERE `value` = :value LIMIT 1")
    suspend fun getWordByValue(value: String): Word

    @Query("SELECT * FROM word WHERE `topic` = :topic")
    suspend fun getWordByTopic(topic: Int): List<Word>

    @Query("UPDATE word SET `right` = `right` + :difference, `attempts` = `attempts` + 1, `percentage` = :percent WHERE id = :word_id")
    suspend fun update(word_id: Int, difference: Int, percent: Float)

    @Query("UPDATE word SET `right` = :right, `attempts` = :attempts, `percentage` = :percent WHERE id = :word_id")
    suspend fun updateNoCalculation(word_id: Int, right: Int, attempts: Int, percent: Float)

    @Query("UPDATE word SET `right` = `right` + :difference, `attempts` = `attempts` + 1 WHERE id = :word_id")
    suspend fun update(word_id: Int, difference: Int)

    @Update
    suspend fun update(word: Word)

}