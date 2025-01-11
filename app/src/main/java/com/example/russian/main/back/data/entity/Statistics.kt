package com.example.russian.main.back.data.entity

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

    @ColumnInfo(name = "taskId")
    val taskId: Long,

    @ColumnInfo(name = "attempts")
    val attempts: Int = 0,

    @ColumnInfo(name = "correct")
    val correct: Int = 0,

    @ColumnInfo(name = "displayable-text")
    val displayableText: String
){
    fun winRate(): Double{
        if (attempts == 0){
            return -1.0
        }

        return (correct.toDouble() / attempts.toDouble())
    }
}

class WordStatistics(
    @Embedded
    val word: MyTask,
    @Relation(
        parentColumn = "taskId",
        entityColumn = "taskId",
    )
    val stats: Statistics
)