package com.example.russian.game.repository.impl

import com.example.russian.game.back.data.dao.StatsDao
import com.example.russian.game.back.data.entity.Statistics
import com.example.russian.game.back.data.entity.playlist.Playlist
import com.example.russian.game.back.data.entity.playlist.PlaylistPositions
import com.example.russian.game.repository.arch.StatsScreenRepositoryInterface
import com.example.russian.main.Id
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import org.burnoutcrew.reorderable.ItemPosition
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class StatsScreenRepository @Inject constructor(
    private val dao: StatsDao,
) : StatsScreenRepositoryInterface {


    override fun allPlaylistsFlow(): Flow<List<Playlist>> {
        return dao.allPlaylistsFlow()
    }

    override suspend fun updatePlaylistPositions(playlists: List<Playlist>) {
        dao.updatePositionsOnScreen(playlists)
    }

    override suspend fun getPlaylistPosition(): List<PlaylistPositions> {
        return dao.getPlaylistPosition()
    }

    private val orderedPlaylists = MutableStateFlow<List<Playlist>>(emptyList())

    fun move(from: ItemPosition, to: ItemPosition){
        orderedPlaylists.update {
            val newValue = it.toMutableList().apply {
                add(to.index, removeAt(from.index))
            }
            newValue
        }
    }

    override fun playlistTasks(playlistId: Id): Flow<List<Statistics>>{

        return dao.statsFromPlaylist(playlistId).map { it.words }

    }

}