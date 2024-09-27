package com.example.russian.ui.state

import com.example.russian.ui.draw.common.SimpleBooleanState
import com.example.russian.ui.draw.stats.comp.PlaylistViewStateParent
import com.example.russian.ui.draw.stats.comp.SortFilterViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class FilterScreenData(
    val playlistState: MutableStateFlow<PlaylistViewStateParent>,
    val sortState: MutableStateFlow<SortFilterViewState>,
    val answerState: MutableStateFlow<SimpleBooleanState>
)