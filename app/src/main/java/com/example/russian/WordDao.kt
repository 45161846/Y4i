package com.example.russian

import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface WordDao {

    @Insert
    suspend fun insert(word: Word)

//    @Query("SELECT * FROM word ORDER BY string ASC")
//    fun getSortedWords():List<Word>

    @Query("SELECT * FROM word")
    fun getAllWords(): Flow<List<Word>>

}