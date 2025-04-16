package com.example.russian.main.prac.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.russian.game.back.data.dao.StatsDao
import com.example.russian.game.back.data.entity.playlist.Playlist
import com.example.russian.game.repository.arch.StatsScreenRepositoryInterface
import com.example.russian.game.repository.impl.StatsScreenRepository
import com.example.russian.main.Id
import com.example.russian.main.data.local.source.LocalDetailsRepo
import com.example.russian.main.prac.PracScreenStage
import com.example.russian.main.prac.details.PlaylistContent
import com.example.russian.main.prac.details.PlaylistDetailsUiState
import com.example.russian.main.prac.details.bottom.BottomFilterActions
import com.example.russian.main.settings.SettingsHolder
import com.example.russian.main.stats.comp.filter.SortDirection
import com.example.russian.main.stats.comp.filter.SortType
import com.example.russian.main.util.mutableStateIn
import com.example.russian.main.util.toCardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.burnoutcrew.reorderable.ItemPosition
import javax.inject.Inject


@HiltViewModel
class LocalPracViewModel @Inject constructor(
    statsDao: StatsDao,
    private val detailsRepo: LocalDetailsRepo,
    private val settingsHolder: SettingsHolder
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

    private val searchPref = MutableStateFlow("")

    val filterState = detailsRepo.filterState
    val filterActions = detailsRepo.filterActions

    val actions: BottomFilterActions = BottomFilterActions(
        onShowUnanswered = detailsRepo.filterActions.onShowUnanswered,
        onSortTypeClick = {},
        onBoundsChange = {}
    )

    val detailsUiState =
        combine(
            detailsRepo.detailsUiState,
            detailsRepo.content,
            searchPref,
            filterState
        ) { details, content, pref, filter ->
            val filteredContent = when (content) {
                is PlaylistContent.Local.Loading -> {
                    searchPref.value = ""
                    content
                }

                is PlaylistContent.Local.Content -> {

                    val cards =
                        (if (filter.showUnanswered.not()) content.cards.filter { it.hasBeenAnswered }
                        else content.cards)
                            .filter {
                                it.hasBeenAnswered.not() || filter.bounds.match(it.winRate * 100)
                            }
                            .filter {
                                it.text.lowercase().contains(pref)
                            }
                            .filter {
                                it.text.lowercase().contains(pref)
                            }

                    val sortedCards = when (filter.sortType) {
                        is SortType.ALPHABETICAL -> cards.sortedBy {
                            it.text.lowercase()
                        }

                        is SortType.BY_WIN_RATE -> cards.sortedBy {
                            it.winRate
                        }
                    }

                    content.copy(
                        cards = if (filter.sortType.direction == SortDirection.UP) sortedCards.reversed()
                        else sortedCards
                    )
                }
            }

            PlaylistDetailsUiState.Local.Details(details, filteredContent)
        }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                PlaylistDetailsUiState.Local.Loading
            )

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

    fun onPlaylistClick(playlistId: Id) {
        _playlists.value.find {
            it.id == playlistId
        }?.let {
            detailsRepo.open(it)
            viewModelScope.launch {

                delay(2000)

                combine(
                    repo.playlistTasks(it.id),
                    settingsHolder.displaySettingsFlow
                ) { tasks, display ->
                    PlaylistContent.Local.Content(
                        tasks.map {
                            it.toCardUiState(display)
                        }
                    )
                }.collectLatest { content ->
                    detailsRepo.updateContent(content)
                }
            }
        }
    }

    fun search(pref: String) {
        searchPref.update { pref }
    }


}