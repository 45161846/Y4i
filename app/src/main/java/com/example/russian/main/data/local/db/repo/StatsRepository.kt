package com.example.russian.main.data.local.db.repo

import com.example.russian.main.Id
import com.example.russian.game.back.data.dao.StatsDao
import com.example.russian.game.back.data.entity.Statistics
import com.example.russian.game.back.data.entity.playlist.Playlist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LocalStatsRepository @Inject constructor(
    private val localDataSource: StatsDao
): StatsRepositoryApi {

    override var cashedStats: List<Statistics> = emptyList()
    override var filteredStats: List<Statistics> = emptyList()

    override fun allStats(): Flow<List<Statistics>> {
        return localDataSource.observeAllStats().onEach {
            cashedStats = it
        }
    }

    override fun allPlaylists(): Flow<List<Playlist>> {
        return localDataSource.allPlaylistsFlow()
    }

    override suspend fun statById(taskId: Id): Flow<Statistics> {
        return localDataSource.wordStats(taskId)
    }

    override suspend fun allPlaylistIdContainTask(taskId: Id): List<Id> {
        return localDataSource.allPlaylistIdContainTask(taskId)
    }
}

interface StatsRepositoryApi {
    var cashedStats: List<Statistics>
    var filteredStats: List<Statistics>

    fun allStats() : Flow<List<Statistics>>

    fun allPlaylists() : Flow<List<Playlist>>

    suspend fun statById(taskId: Id): Flow<Statistics>

    suspend fun allPlaylistIdContainTask(taskId: Id): List<Id>
}