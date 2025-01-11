package com.example.russian.architectured.data.local.source

import com.example.russian.architectured.data.local.db.entity.Task
import com.example.russian.main.back.data.dao.StatsDao
import com.example.russian.main.back.data.entity.MyTask
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

//@Singleton
//class StatsRepo @Inject constructor(
//    private val localDataSource: StatsDao
//): StatsRepoApi {
//
//}

interface StatsRepoApi{

    fun getTasksStream(): Flow<List<Task>>

    suspend fun getTasks(forceUpdate: Boolean = false): List<Task>

    suspend fun refresh()

    fun getTaskStream(taskId: String): Flow<Task?>

    suspend fun getTask(taskId: String, forceUpdate: Boolean = false): Task?

    suspend fun refreshTask(taskId: String)

    suspend fun createTask(title: String, description: String): String

    suspend fun updateTask(taskId: String, title: String, description: String)

    suspend fun completeTask(taskId: String)

    suspend fun activateTask(taskId: String)

    suspend fun clearCompletedTasks()

    suspend fun deleteAllTasks()

    suspend fun deleteTask(taskId: String)

}