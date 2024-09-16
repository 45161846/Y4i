package com.example.russian.ui.state

import com.example.russian.ui.draw.stats.comp.PlaylistViewState
import com.example.russian.ui.draw.stats.comp.SortFilterViewState
import com.example.russian.ui.draw.stats.comp.UnansweredViewState
import kotlinx.coroutines.flow.StateFlow

data class FilterScreenData(
    val playlistState: StateFlow<PlaylistViewState>,
    val sortState: StateFlow<SortFilterViewState>,
    val answerState: StateFlow<UnansweredViewState>
)