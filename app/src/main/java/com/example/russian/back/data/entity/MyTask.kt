package com.example.russian.back.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity
class MyTask (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("taskId")
    var id: Long = 0,

    @ColumnInfo("value")
    val value: String,

    @ColumnInfo("topic")
    val topic: Int,
){
    override fun toString(): String {
        return "Task id: $id. Value: $value"
    }
}

class TaskNoPartOfTask(
    @Embedded
    val word: MyTask,

    @Relation(
        entity = TaskData::class,
        parentColumn = "taskId",
        entityColumn = "taskId"
    )
    val taskData: TaskData
)

class TaskPartOfTask(
    @Embedded
    val taskNoPartOfTask: TaskNoPartOfTask,

    @Relation(
        parentColumn = "taskId",
        entityColumn = "taskId"
    )
    val PartOfTasks: List<PartOfTask>
)