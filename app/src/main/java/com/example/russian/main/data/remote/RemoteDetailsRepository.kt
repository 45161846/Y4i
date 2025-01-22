package com.example.russian.main.data.remote

import com.example.russian.main.Id
import com.example.russian.main.prac.details.PlaylistContent
import com.example.russian.main.prac.details.PlaylistOverView
import com.example.russian.main.prac.remote.PlaylistColor
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RemoteDetailsRepo @Inject constructor(
    private val playlistRepo: RemotePlaylistRepository,
    private val webSource: PlaylistWebSource
): RemoteDetailsRepoInterface() {

    override val content: MutableStateFlow<PlaylistContent.Remote>
    = MutableStateFlow(PlaylistContent.Remote.Loading)

    override val overView: MutableStateFlow<PlaylistOverView.Remote>
    = MutableStateFlow(PlaylistOverView.Remote.Loading)

    override suspend fun open(playlistId: Id) {
        val opened = playlistRepo.cashedPlaylists.value.find {
            it.remoteId == playlistId
        }
        content.value = PlaylistContent.Remote.Loading

        opened?.let { playlist ->
            overView.value = PlaylistOverView.Remote.OverView(
                title = playlist.title,
                description = playlist.description,
                color = PlaylistColor.Regular,
                previewTasks = playlist.previewTasks,
                rating = playlist.rating,
                capacity = playlist.capacity
            )
        }

        nextTasks(playlistId, 0, 20)
    }

    override suspend fun nextTasks(remotePlaylistId: Id, after: Int, amount: Int) {
        content.value.let{ prev ->
            val previous = when(prev){
                is PlaylistContent.Remote.Loading -> PlaylistContent.Remote.Content(emptyList())
                is PlaylistContent.Remote.Content -> prev
            }

            content.value = PlaylistContent.Remote.Content(
                previous.cards + webSource.getTasks(remotePlaylistId, after, amount)
            )
        }
    }
}


abstract class RemoteDetailsRepoInterface {

    abstract val overView: MutableStateFlow<PlaylistOverView.Remote>
    abstract val content: MutableStateFlow<PlaylistContent.Remote>

    /**
     * Used to load new playlist's tasks at the end when all on page are viewed
     * @param remotePlaylistId - playlist id where to get tasks from
     * @param after - index that stands for the last element currently loaded
     * @param amount - how many next card to get from server
     */
    abstract suspend fun nextTasks(remotePlaylistId: Id, after: Int, amount: Int)

    abstract suspend fun open(playlistId: Id)
}