package com.example.russian.ui.state

import com.example.russian.enums.SortType
import com.example.russian.back.data.entity.playlist.Playlist

sealed class FilterState

data class MarkedPlaylist(
    val playlist: Playlist,
    var marked: Boolean
)

data class FilterSettingData(
    var playlists: List<MarkedPlaylist>,
    var sortTypes: List<SortType>,
    var showUnanswered: Boolean,
)