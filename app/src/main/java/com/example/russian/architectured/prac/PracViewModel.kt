package com.example.russian.architectured.prac

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.russian.architectured.util.mutableStateIn
import com.example.russian.main.back.data.dao.StatsDao
import com.example.russian.main.back.data.entity.playlist.Playlist
import com.example.russian.main.back.data.entity.playlist.PlaylistPositions
import com.example.russian.main.repository.arch.StatsScreenRepositoryInterface
import com.example.russian.main.repository.impl.StatsScreenRepository
import com.example.russian.main.ui.draw.practice.PracScreenActions
import com.example.russian.main.ui.state.PracScreenStage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.burnoutcrew.reorderable.ItemPosition
import javax.inject.Inject


@HiltViewModel
class PracViewModel @Inject constructor(
    statsDao: StatsDao
) : ViewModel() {

    private val repo: StatsScreenRepositoryInterface = StatsScreenRepository(
        statsDao
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _playlists: MutableStateFlow<List<Playlist>> =
        repo.allPlaylistsFlow().mapLatest {
            val positions = repo.getPlaylistPosition()
            it.sortedBy { playlist ->
                positions.firstOrNull { pos ->
                    pos.playlistId == playlist.id
                }?.positionIndex ?: Int.MAX_VALUE
            }
        }
            .mutableStateIn(
                viewModelScope,
                emptyList()
            )

    val pracScreenLocalUiState = _playlists
        .map {
            PracScreenStage.Local.Data(it)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            PracScreenStage.Local.Loading,
        )


    val pracScreenRemoteUiState: StateFlow<PracScreenStage.Remote> =
        MutableStateFlow<PracScreenStage.Remote>(PracScreenStage.Remote.Loading)

    fun onMovePlaylist(from: ItemPosition, to: ItemPosition) {

        _playlists.update {
            val newValue = it.toMutableList().apply {
                add(to.index, removeAt(from.index))
            }
            viewModelScope.launch(Dispatchers.IO) {
                repo.updatePlaylistPositions(newValue)
            }
            newValue
        }
    }
}