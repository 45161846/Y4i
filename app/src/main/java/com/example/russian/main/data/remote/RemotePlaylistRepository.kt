package com.example.russian.main.data.remote

import androidx.compose.runtime.Stable
import com.example.remotelogin.wrappers.RequestResult
import com.example.russian.main.Id
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.ArrayDeque
import java.util.Queue
import javax.inject.Inject
import javax.inject.Singleton

typealias WordStack = List<Id>


@Singleton
class RemotePlaylistRepository @Inject constructor(
    private val webSource: PlaylistWebSource
) : RemotePlaylistRepositoryAPI {

    override val cashedPlaylists = MutableStateFlow(emptyList<RemotePlaylist>())

    private val downloadQueue: MutableStateFlow<Queue<WordStack>> = MutableStateFlow(ArrayDeque())

    override suspend fun nextPlaylist(
        after: Int,
        amount: Int,
        onError: (String) -> Unit,
        onNotingMore: () -> Unit
    ) {

        when (val result = webSource.getPlaylists(after, amount)) {
            is RequestResult.PlaylistPage.Error -> onError(result.message)
            is RequestResult.PlaylistPage.NotingMore -> onNotingMore()
            is RequestResult.PlaylistPage.Data -> cashedPlaylists.update {
                it + result.playlists
            }
        }

    }

}


interface RemotePlaylistRepositoryAPI {
    abstract val cashedPlaylists: Flow<List<RemotePlaylist>>

    /**
     * Used to load new playlists at the end when all on page are viewed
     * @param after - index that stands for the last element currently loaded
     * @param amount - how many next card to get from server
     * @param onError - callback to handle error in viewmodel
     * @param onNotingMore - callback when all of the playlists are loaded and nothing more to show
     */
    abstract suspend fun nextPlaylist(
        after: Int,
        amount: Int,
        onError: (String) -> Unit,
        onNotingMore: () -> Unit
    )

}

@Stable
@Serializable
data class RemotePlaylist(
    val remoteId: Id,
    val title: String,
    val description: String,
    val rating: Float,
    val capacity: Long,
    val previewTasks: List<String>
)

@Serializable
data class PreviewTask(
    val remoteId: Id,
    val previewText: String
)

@Serializable
@SerialName("nothing-found")
data object NothingFound