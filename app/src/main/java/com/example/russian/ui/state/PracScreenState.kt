package com.example.russian.ui.state

import com.example.russian.back.data.entity.playlist.Playlist

sealed class PracScreenStage {
    data object Loading : PracScreenStage()

    class PracScreenState(
        var playlists: List<Playlist>
    ): PracScreenStage()

}