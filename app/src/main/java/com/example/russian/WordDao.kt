package com.example.russian

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Insert
    suspend fun insert(word: Word)

    @Insert
    suspend fun insert(words: List<Word>)

    @Query("SELECT * FROM word")
    fun getAllWords(): Flow<List<Word>>

    @Query("SELECT * FROM word WHERE `id` = :requested_id")
    fun getWordById(requested_id: Int): Flow<List<Word>>

    @Query("SELECT * FROM word WHERE `topic` = :topic")
    fun getWordByTopic(topic: Int):List<Word>

    @Query("UPDATE word SET `right` = :r, `wrong` = :w WHERE id = :word_id")
    fun update(word_id: Int, r: Int, w: Int)

    @Query("UPDATE word SET `right` = `right` + :difference, `wrong` = `wrong` - :difference WHERE id = :word_id")
    fun update(word_id: Int, difference: Int)

    @Update
    fun update(word: Word)

}