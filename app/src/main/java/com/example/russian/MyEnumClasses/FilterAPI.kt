package com.example.russian.MyEnumClasses

import androidx.compose.runtime.State
import com.example.russian.architecture2.ui.state.FilterSettingData
import com.example.russian.architecture2.ui.state.MarkedPlaylist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface FilterAPI {

    fun save(filter: FilterSettingData)

    fun defaultPlaylists(): List<MarkedPlaylist>

    fun sortTypes(): Array<SortType>

    fun defaultSortType(): SortType

    fun defaultShowUnanswered(): Boolean
}