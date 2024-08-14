package com.example.russian.architecture2.repository.impl

import com.example.russian.architecture2.backend.data.dao.StatsDao
import com.example.russian.architecture2.backend.data.entity.WordStatistics
import com.example.russian.architecture2.repository.arch.StatsScreenRepositoryInterface
import com.example.russian.architecture2.ui.state.FilterSettingData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat

class StatsScreenRepository : StatsScreenRepositoryInterface {

    private lateinit var dao: StatsDao

    override fun setDao(statsDao: StatsDao) {
        dao = statsDao
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun wordsFiltered(filter: FilterSettingData): Flow<List<WordStatistics>> {

        val ids = ArrayList<Long>(initialCapacity = filter.playlists.size)

        filter.playlists.forEach {
            if (it.marked) {
                ids.add(it.playlist.id)
            }
        }

        return dao
            .wordsFromPlaylists(ids)
            .flatMapConcat {

                dao.wordsStats(
                    List(it.size) { ind ->
                        it[ind].id
                    }
                )

            }

    }
}