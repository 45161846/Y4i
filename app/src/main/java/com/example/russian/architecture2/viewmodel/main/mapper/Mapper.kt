package com.example.russian.architecture2.viewmodel.main.mapper

import com.example.russian.MyEnumClasses.SortType
import com.example.russian.MyEnumClasses.SortTypeMode
import com.example.russian.MyEnumClasses.SortTypesEnum
import com.example.russian.architecture2.ui.state.FilterSettingData
import com.example.russian.architecture2.ui.state.FilterState
import com.example.russian.architecture2.ui.state.MarkedPlaylist

interface Mapper{
    fun filterStateToFilterSettingData(filter: FilterState, playlists: List<MarkedPlaylist>): FilterSettingData
}