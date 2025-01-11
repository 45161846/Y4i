package com.example.russian.architectured.data.local.db.repo

import com.example.russian.architectured.Id
import com.example.russian.architectured.data.local.db.dao.StatsDao
import com.example.russian.architectured.data.local.db.entity.Task
import com.example.russian.architectured.data.local.db.entity.TaskStat
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LocalStatsRepository @Inject constructor(
    private val localDataSource: StatsDao
): StatsRepositoryApi {
    override fun allStats(): Flow<List<TaskStat>> {
        return localDataSource.observeAllStats()
    }

    override suspend fun statById(taskId: Id): TaskStat {
        return localDataSource.statById(taskId)
    }
}

interface StatsRepositoryApi {

    fun allStats() : Flow<List<TaskStat>>

    suspend fun statById(taskId: Id): TaskStat
}