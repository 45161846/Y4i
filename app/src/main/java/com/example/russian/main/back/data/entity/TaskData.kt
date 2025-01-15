package com.example.russian.main.back.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.russian.architectured.Id


@Entity
class TaskData(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task-data-id")
    var id: Id = Id(0L),


    @ColumnInfo(name = "taskId")
    val taskId: Id,


    @ColumnInfo(name = "context")
    val contextText: String,

    @ColumnInfo(name = "displayable-text")
    val displayableText: String
)