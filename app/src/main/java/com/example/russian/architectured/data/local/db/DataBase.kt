package com.example.russian.architectured.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.russian.architectured.data.local.db.dao.StatsDao
import com.example.russian.architectured.data.local.db.dao.TaskDao
import com.example.russian.architectured.data.local.db.entity.Answer
import com.example.russian.architectured.data.local.db.entity.Task
import com.example.russian.architectured.data.local.db.entity.TaskStat


@Database(
    entities = [
        Task::class,
        TaskStat::class,
        Answer::class
    ],
    version = 1
)
abstract class TaskDataBase: RoomDatabase() {

    abstract fun taskDao() : TaskDao
    abstract fun statDao() : StatsDao

}

