package com.example.russian.architecture.data.dao.components

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.russian.architecture.data.entity.Word
import com.example.russian.architecture.data.entity.components.NewWord
import kotlinx.coroutines.flow.Flow


@Dao
interface NewWordDao {

    @Insert
    suspend fun insert(word: NewWord)

    @Insert
    suspend fun insert(words: List<NewWord>)

    @Query("SELECT (SELECT COUNT(*) FROM NewWord) == 0")
    suspend fun checkIfNoneExist(): Boolean


    @Query("SELECT * FROM NEWWORD")
    fun getAllWords(): Flow<List<NewWord>>



}