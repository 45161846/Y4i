package com.example.russian.main.back.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.russian.architectured.Id
import com.example.russian.architectured.TaskType


@Entity
class Statistics (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "statsId")
    var id: Id = Id(0L),

    @ColumnInfo(name = "taskId")
    val taskId: Id,

    @ColumnInfo(name = "attempts")
    val attempts: Int = 0,

    @ColumnInfo(name = "correct")
    val correct: Int = 0,

    @ColumnInfo(name = "displayable-text")
    val displayableText: String,

    val type: TaskType
){
    fun winRate(): Double{
        if (attempts == 0) return 0.0
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