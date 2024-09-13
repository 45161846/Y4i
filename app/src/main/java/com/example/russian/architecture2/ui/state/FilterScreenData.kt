package com.example.russian.architecture2.ui.state

import com.example.russian.architecture2.ui.draw.stats.comp.PlaylistViewState
import com.example.russian.architecture2.ui.draw.stats.comp.SortFilterViewState
import com.example.russian.architecture2.ui.draw.stats.comp.UnansweredViewState
import com.example.russian.architecture2.ui.draw.stats.screen.FilterScreenActions
import kotlinx.coroutines.flow.StateFlow

data class FilterScreenData(
    val playlistState: StateFlow<PlaylistViewState>,
    val sortState: StateFlow<SortFilterViewState>,
    val answerState: StateFlow<UnansweredViewState>
)