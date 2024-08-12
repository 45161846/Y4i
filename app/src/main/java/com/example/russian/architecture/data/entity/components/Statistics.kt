package com.example.russian.architecture.data.entity.components

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class Statistics (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "statsId")
    var id: Int = 0,

    @ColumnInfo(name = "wordId")
    val wordId: Int,

    @ColumnInfo(name = "attempts")
    val attempts: Int = 0,

    @ColumnInfo(name = "correct")
    val correct: Int = 0,

    @ColumnInfo(name = "displayable-text")
    val displayableText: String
){
    fun winRate() = (correct.toDouble() / attempts.toDouble())
}