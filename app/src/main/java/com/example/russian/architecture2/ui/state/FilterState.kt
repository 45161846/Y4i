package com.example.russian.architecture2.ui.state

import com.example.russian.MyEnumClasses.SortType
import com.example.russian.architecture2.backend.data.entity.playlist.Playlist

sealed class FilterState{

    data object Default: FilterState()

    data class Custom(
        val filterData: FilterSettingData,
        val saveButtonClick: (FilterState) -> Unit,
        val resetButtonClick: (FilterSettingData) -> Unit
    ) : FilterState()
}

data class MarkedPlaylist(
    val playlist: Playlist,
    var marked: Boolean
)

data class FilterSettingData(
    var playlists: List<MarkedPlaylist>,
    var sortTypes: List<SortType>,
    var showUnanswered: Boolean,
){
    fun copy(other: FilterSettingData){
        this.playlists = other.playlists
        this.sortTypes = other.sortTypes
        this.showUnanswered = other.showUnanswered
    }
}