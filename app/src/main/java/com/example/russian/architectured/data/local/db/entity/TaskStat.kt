package com.example.russian.architectured.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.russian.architectured.Counter
import com.example.russian.architectured.Id
import com.example.russian.architectured.TaskType
import com.example.russian.architectured.util.Converters

@Entity
data class TaskStat(
    //matches task id
    @PrimaryKey(autoGenerate = false)
    val id: Id,

    val correct: Counter,
    val incorrect: Counter,

    val displayText: String,

    @TypeConverters(Converters::class)
    val taskType: TaskType
)
