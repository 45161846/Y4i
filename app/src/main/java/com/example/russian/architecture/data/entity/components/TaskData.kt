package com.example.russian.architecture.data.entity.components

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class TaskData(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task-data-id")
    var id: Int = 0,


    @ColumnInfo(name = "wordId")
    val wordId: Int,


    @ColumnInfo(name = "context")
    val contextText: String,

    @ColumnInfo(name = "displayable-text")
    val displayableText: String
) {
}