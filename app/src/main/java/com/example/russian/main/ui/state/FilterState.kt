package com.example.russian.main.ui.state

import com.example.russian.main.back.data.entity.playlist.Playlist
import com.example.russian.main.enums.SortType

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