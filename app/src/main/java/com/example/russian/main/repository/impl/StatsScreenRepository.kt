package com.example.russian.main.repository.impl

import androidx.lifecycle.viewModelScope
import com.example.russian.architectured.stats.comp.filter.FilterState
import com.example.russian.main.back.data.dao.StatsDao
import com.example.russian.main.back.data.entity.Statistics
import com.example.russian.main.back.data.entity.playlist.Playlist
import com.example.russian.main.back.data.entity.playlist.PlaylistPositions
import com.example.russian.main.enums.getSortTypeFromList
import com.example.russian.main.repository.arch.StatsScreenRepositoryInterface
import com.example.russian.main.tool.DifferentTypeSort
import com.example.russian.main.ui.state.FilterSettingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.burnoutcrew.reorderable.ItemPosition
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class StatsScreenRepository @Inject constructor(
    private val dao: StatsDao,
) : StatsScreenRepositoryInterface {

    override suspend fun wordsFiltered(filter: FilterState): Flow<List<Statistics>> {

        val ids = filter.markedPlaylists
            .filter {
                it.marked
            }
            .map { it.playlist.id }


        val flows = List(ids.size) { ind ->
            dao.statsFromPlaylist(ids[ind]).map { playlist ->
                playlist.words
            }
        }
        return combine(flows, transform = { arr ->
            arr.toList().flatten()
        })
            .map { words ->
                DifferentTypeSort(filter.sortType, words).sort()
            }
            .map { words ->
                if (filter.showUnanswered) {
                    words
                } else {
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

    override suspend fun updatePlaylistPositions(playlists: List<Playlist>) {
        dao.updatePositionsOnScreen(playlists)
    }

    override suspend fun getPlaylistPosition(): List<PlaylistPositions> {
        return dao.getPlaylistPosition()
    }

    override fun getPlaylistPositionFlow(): Flow<List<PlaylistPositions>> {
        return dao.getPlaylistPositionFlow()
    }

    val orderedPlaylists = MutableStateFlow<List<Playlist>>(emptyList())

    fun move(from: ItemPosition, to: ItemPosition){
        orderedPlaylists.update {
            val newValue = it.toMutableList().apply {
                add(to.index, removeAt(from.index))
            }
            newValue
        }
    }



}