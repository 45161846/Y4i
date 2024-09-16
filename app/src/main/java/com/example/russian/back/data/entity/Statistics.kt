package com.example.russian.back.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity
class Statistics (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "statsId")
    var id: Long = 0,

    @ColumnInfo(name = "wordId")
    val wordId: Long,

    @ColumnInfo(name = "attempts")
    val attempts: Int = 0,

    @ColumnInfo(name = "correct")
    val correct: Int = 0,

    @ColumnInfo(name = "displayable-text")
    val displayableText: String
){
    fun winRate() = (correct.toDouble() / attempts.toDouble())
}

class WordStatistics(
    @Embedded
    val word: NewWord,
    @Relation(
        parentColumn = "wordId",
        entityColumn = "wordId",
    )
    val stats: Statistics
)