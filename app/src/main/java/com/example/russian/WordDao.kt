package com.example.russian

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.russian.Word

@Dao
interface WordDao {

    @Insert
    suspend fun insert(word: Word)

    @Insert
    suspend fun insert(words: List<Word>)

    @Query("SELECT * FROM word")
    suspend fun getAllWords(): List<Word>

    @Query("SELECT * FROM word WHERE `id` = :requested_id")
    suspend fun getWordById(requested_id: Int): List<Word>

    @Query("SELECT * FROM word WHERE `topic` = :topic")
    suspend fun getWordByTopic(topic: Int): List<Word>

    @Query("UPDATE word SET `right` = :r, `attempts` = :attempts, `percentage` = (:r / :attempts) WHERE id = :word_id")
    suspend fun update(word_id: Int, r: Int, attempts: Int)

    @Query("UPDATE word SET `right` = `right` + :difference, `attempts` = `attempts` + 1, `percentage` = (`right` + :difference) / (`attempts` + 1) WHERE id = :word_id")
    suspend fun update(word_id: Int, difference: Int)

    @Update
    suspend fun update(word: Word)

}