package com.example.russian.architectured.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.russian.architectured.Counter
import com.example.russian.architectured.Id
import com.example.russian.architectured.data.local.db.entity.Task
import com.example.russian.architectured.data.local.db.entity.TaskStat
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM TASK")
    fun observeAll(): Flow<List<Task>>

    @Insert
    suspend fun addTask(task: Task) : Long

    @Insert
    suspend fun addStat(stat: TaskStat)

    suspend fun createTask(task: Task){
        val id = addTask(task)
        addStat(TaskStat(
            Id(id), 0, 0, "Word $id" ,task.type
        ))
    }
}