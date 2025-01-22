package com.example.russian.main.data.remote

import com.example.russian.main.Id
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.Serializable
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RemotePlaylistRepository @Inject constructor(
    private val webSource: PlaylistWebSource
): RemotePlaylistRepositoryAPI() {

    override val cashedPlaylists = MutableStateFlow(emptyList<RemotePlaylist>())

    override suspend fun nextPlaylist(after: Int, amount: Int) {

        cashedPlaylists.update {
            it + webSource.getPlaylists(after, amount)
        }

    }

}

abstract class RemotePlaylistRepositoryAPI{
    abstract val cashedPlaylists: Flow<List<RemotePlaylist>>

    /**
     * Used to load new playlists at the end when all on page are viewed
     * @param after - index that stands for the last element currently loaded
     * @param amount - how many next card to get from server
     */
    abstract suspend fun nextPlaylist(after: Int, amount: Int)

}


@Serializable
data class RemotePlaylist(
    val remoteId: Id,
    val title: String,
    val description: String,
    val rating: Float,
    val capacity: Int,
    val previewTasks: List<String>
)