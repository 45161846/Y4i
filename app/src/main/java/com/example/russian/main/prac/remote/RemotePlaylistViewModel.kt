package com.example.russian.main.prac.remote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remotelogin.wrappers.RequestResult
import com.example.russian.main.Id
import com.example.russian.main.data.ToastHandler
import com.example.russian.main.data.remote.LateLoadingAPI
import com.example.russian.main.data.remote.PreviewTask
import com.example.russian.main.data.remote.RemoteDetailsRepo
import com.example.russian.main.data.remote.RemotePlaylist
import com.example.russian.main.data.remote.RemotePlaylistRepository
import com.example.russian.main.prac.PracScreenStage
import com.example.russian.main.prac.RemotePlaylistUi
import com.example.russian.main.prac.details.PlaylistContent
import com.example.russian.main.prac.details.PlaylistDetailsUiState
import com.example.russian.main.prac.details.PlaylistOverView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


@HiltViewModel
class RemotePlaylistViewModel @Inject constructor(
    private val detailsRepo: RemoteDetailsRepo,
    private val playlistRepo: RemotePlaylistRepository,
    private val toastHandler: ToastHandler,
    private val loadingExecutor: LateLoadingAPI
) : ViewModel() {


    private val loadingPlaylists = List(10) {
        RemotePlaylistUi.Loading
    }

    private val lastPlaylists: MutableStateFlow<List<RemotePlaylistUi>> = MutableStateFlow(
        loadingPlaylists
    )

    init {
        viewModelScope.launch {
            loadingExecutor.loadingQueue
                .collect {
                    loadingExecutor.loadTasks(it)
                }
        }
    }

    val screenUiState: StateFlow<PracScreenStage.Remote> =
        combine(playlistRepo.cashedPlaylists, lastPlaylists) { playlists, last ->

            val newPlaylists = playlists.map {
                RemotePlaylistUi.Data(it)
            } + last

            PracScreenStage.Remote(
                newPlaylists, playlists.size
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            PracScreenStage.Remote(loadingPlaylists, 0)
        )

    val detailUiState: StateFlow<PlaylistDetailsUiState.Remote> =
        combine(detailsRepo.overView, detailsRepo.content) { overview, content ->
            PlaylistDetailsUiState.Remote.Details(
                overview, content
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            PlaylistDetailsUiState.Remote.Loading
        )

    fun open(remoteId: Id) {
        viewModelScope.launch {
            detailsRepo.open(remoteId)
        }
    }

    fun appendPlaylists(lastIndex: Int) {
        viewModelScope.launch {
            playlistRepo.nextPlaylist(lastIndex, 20, onError = { error ->

                lastPlaylists.update {
                    listOf(RemotePlaylistUi.EndCard.Error(error))
                }

            }, onNotingMore = {

                lastPlaylists.update {
                    listOf(RemotePlaylistUi.EndCard.NothingMore)
                }

            })
        }
    }

    fun download(
        playlist: PlaylistOverView.Remote.OverView,
        tasks: PlaylistContent.Remote
    ) {
        when (tasks) {
            is PlaylistContent.Remote.Loading -> return
            is PlaylistContent.Remote.Content -> {
                viewModelScope.launch {
                    detailUiState.value.let {
                        loadingExecutor.addNewPlaylist(
                            RemotePlaylist(
                                playlist.id,
                                playlist.title,
                                playlist.description,
                                playlist.rating,
                                playlist.capacity,
                                playlist.previewTasks
                            ),
                            tasks.cards
                        )
                    }
                }
            }
        }
    }
}