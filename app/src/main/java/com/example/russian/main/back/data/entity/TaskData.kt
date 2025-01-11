package com.example.russian.main.back.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class TaskData(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task-data-id")
    var id: Long = 0,


    @ColumnInfo(name = "taskId")
    val taskId: Long,


    @ColumnInfo(name = "context")
    val contextText: String,

    @ColumnInfo(name = "displayable-text")
    val displayableText: String
)