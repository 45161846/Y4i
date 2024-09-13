package com.example.russian.architecture2.repository.impl

import com.example.russian.MyEnumClasses.getSortTypeFromList
import com.example.russian.architecture2.backend.data.dao.StatsDao
import com.example.russian.architecture2.backend.data.entity.Statistics
import com.example.russian.architecture2.backend.data.entity.playlist.Playlist
import com.example.russian.architecture2.repository.arch.StatsScreenRepositoryInterface
import com.example.russian.architecture2.ui.state.FilterSettingData
import com.example.russian.toolPackage.DifferentTypeSort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class StatsScreenRepository : StatsScreenRepositoryInterface {

    private lateinit var dao: StatsDao

    override fun setDao(statsDao: StatsDao) {
        dao = statsDao
    }

    override suspend fun wordsFiltered(filter: FilterSettingData): Flow<List<Statistics>> {

        val ids = ArrayList<Long>(filter.playlists.size)

        filter.playlists.forEach {
            if (it.marked) {
                ids.add(it.playlist.id)
            }
        }


        val flows = List(ids.size){ ind ->
            dao.statsFromPlaylist(ids[ind]).map { playlist ->
                playlist.words
            }
        }
        return combine(flows, transform = { arr ->
            arr.toList().flatten()
        })
            .map { words ->
                DifferentTypeSort(getSortTypeFromList(filter.sortTypes), words).sort()
            }
            .map { words ->
                if(filter.showUnanswered){
                    words
                }else{
                    words.filter { word ->
                        word.attempts > 0
                    }
                }
            }

    }

    override suspend fun allPlaylists(): List<Playlist> {
        return dao.allPlaylists()
    }

    override fun allPlaylistsFlow(): Flow<List<Playlist>> {
        return dao.allPlaylistsFlow()
    }
}