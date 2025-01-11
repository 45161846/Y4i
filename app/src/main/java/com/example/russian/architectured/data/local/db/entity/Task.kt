package com.example.russian.architectured.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.russian.architectured.Id
import com.example.russian.architectured.TaskType


@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val taskId: Id,

    val type: TaskType
)
