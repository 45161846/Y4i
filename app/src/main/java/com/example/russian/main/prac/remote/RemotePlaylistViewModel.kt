package com.example.russian.main.prac.remote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.russian.main.Id
import com.example.russian.main.data.remote.RemoteDetailsRepo
import com.example.russian.main.data.remote.RemotePlaylistRepository
import com.example.russian.main.prac.PracScreenStage
import com.example.russian.main.prac.details.PlaylistDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RemotePlaylistViewModel @Inject constructor(
    private val detailsRepo: RemoteDetailsRepo,
    private val playlistRepo: RemotePlaylistRepository
) : ViewModel() {

    val screenUiState: StateFlow<PracScreenStage.Remote> =
        combine(playlistRepo.cashedPlaylists, flowOf(null)) { playlists, _ ->
            PracScreenStage.Remote.Data(
                playlists
            )
        }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                PracScreenStage.Remote.Loading
            )

    val detailUiState: StateFlow<PlaylistDetailsUiState.Remote> =
        combine(detailsRepo.overView, detailsRepo.content){overview, content ->
            PlaylistDetailsUiState.Remote.Details(
                overview, content
            )
        }
            .stateIn(
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
            playlistRepo.nextPlaylist(lastIndex, 20)
        }
    }

    init {
        appendPlaylists(0)
    }

}