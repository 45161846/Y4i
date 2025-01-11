package com.example.russian.main.ui.state

import com.example.russian.main.ui.draw.common.SimpleBooleanState
import com.example.russian.main.ui.draw.stats.comp.PlaylistViewStateParent
import com.example.russian.main.ui.draw.stats.comp.SortFilterViewState
import kotlinx.coroutines.flow.MutableStateFlow

data class FilterScreenData(
    val playlistState: MutableStateFlow<PlaylistViewStateParent>,
    val sortState: MutableStateFlow<SortFilterViewState>,
    val answerState: MutableStateFlow<SimpleBooleanState>
)