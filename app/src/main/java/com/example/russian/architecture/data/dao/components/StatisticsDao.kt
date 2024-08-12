package com.example.russian.architecture.data.dao.components

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import com.example.russian.architecture.data.entity.components.NewWord
import com.example.russian.architecture.data.entity.components.Statistics


@Dao
interface StatisticsDao {


    @Query("UPDATE Statistics SET `correct` = :correct, `attempts` = :attempts WHERE `wordId` = :wordID" )
    suspend fun updateStats(wordID: Int, correct: Int, attempts: Int)

    @Insert
    suspend fun addStat(stat: Statistics)


    @Query("SELECT * FROM STATISTICS WHERE `wordId` = :wordId LIMIT 1")
    suspend fun getStats(wordId: Int): Statistics

    @Transaction
    @Query("SELECT * FROM STATISTICS WHERE `wordId` = :wordId LIMIT 1")
    suspend fun getWordWithStats(wordId: Int) : WordStatistics
}


class WordStatistics(
    @Embedded
    val word: NewWord,
    @Relation(
        parentColumn = "new-wordId",
        entityColumn = "wordId",
    )
    val stats: Statistics
)