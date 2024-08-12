package com.example.russian.architecture.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.russian.architecture.data.entity.Spelling
import com.example.russian.architecture.data.entity.WordWithSpellings
import kotlinx.coroutines.flow.Flow


@Dao
interface SpellingDao {

    @Transaction
    @Query("SELECT * FROM word")
    fun getAllSpellings(): Flow<List<WordWithSpellings>>

    @Transaction
    @Query("SELECT * FROM WORD WHERE `wordId` = :wordId LIMIT 1")
    fun getWordSpelling(wordId: Int): Flow<WordWithSpellings>

    @Transaction
    @Query("SELECT * FROM word WHERE `topic` = :topic")
    fun getAllSpellingsByTopic(topic: Int): Flow<List<WordWithSpellings>>


    @Query("SELECT * FROM SPELLING WHERE `spellingId` = :spellingId LIMIT 1")
    fun getSpelling(spellingId: Int): Flow<Spelling>

    @Insert
    suspend fun addSpelling(spelling: Spelling)

    @Insert
    suspend fun addSpellings(spellings: List<Spelling>)
}