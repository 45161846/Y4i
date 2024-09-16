package com.example.russian.enums

import com.example.russian.ui.state.FilterSettingData
import com.example.russian.ui.state.MarkedPlaylist

interface FilterAPI {

    fun save(filter: FilterSettingData)

    fun defaultPlaylists(): List<MarkedPlaylist>

    fun sortTypes(): Array<SortType>

    fun defaultSortType(): SortType

    fun defaultShowUnanswered(): Boolean
}