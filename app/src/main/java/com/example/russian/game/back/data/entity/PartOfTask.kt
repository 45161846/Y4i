package com.example.russian.game.back.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.russian.main.Id


@Entity
data class PartOfTask(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "PartOfTaskId")
    var id: Id = Id(0L),

    @ColumnInfo(name = "taskId")
    var taskId: Id,

    @ColumnInfo(name = "value")
    var value: String,

    var index: Int = 0,

    @ColumnInfo(name = "isCorrect")
    var isCorrect: Boolean
)

data class WordWithPartOfTasks(
    @Embedded val word: MyTask,
    @Relation(
        parentColumn = "id",
        entityColumn = "taskId"
    )
    val PartOfTasks: List<PartOfTask>
)