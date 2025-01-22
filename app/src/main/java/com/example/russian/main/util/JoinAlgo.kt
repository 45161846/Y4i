package com.example.russian.main.util

import com.example.russian.game.back.data.entity.playlist.Playlist
import com.example.russian.main.stats.comp.filter.MarkedPlaylist
import com.example.russian.main.stats.comp.filter.MarkedPlaylistVariant
import com.example.russian.main.stats.comp.filter.SortDirection
import com.example.russian.main.stats.comp.filter.SortType
import com.example.russian.main.stats.comp.filter.reverse

fun join(
    playlists: List<Playlist>,
    checkedPlaylists: MarkedPlaylistVariant
): MarkedPlaylistVariant {

    return when (checkedPlaylists) {
        is MarkedPlaylistVariant.All -> MarkedPlaylistVariant.Partial(playlists.map{
            MarkedPlaylist(it, true)
        })
        is MarkedPlaylistVariant.Partial -> MarkedPlaylistVariant.Partial(playlists.map { play ->
            val old = checkedPlaylists.markedPlaylists.find { checked ->
                checked.playlist == play
            }
            MarkedPlaylist(play, old?.marked ?: true)
        }
        )
    }
}

fun List<MarkedPlaylist>.update(clicked: MarkedPlaylist) = this
    .map {
        if (it.playlist.id == clicked.playlist.id) it.copy(marked = it.marked.not())
        else it
    }

fun List<SortType>.update(clicked: SortType) = this.map {
    when (clicked) {
        is SortType.ALPHABETICAL -> if (it == clicked) clicked.copy(direction = clicked.direction.reverse()) else clicked.copy(
            direction = SortDirection.UNSPECIFIED
        )

        is SortType.BY_WIN_RATE -> if (it == clicked) clicked.copy(direction = clicked.direction.reverse()) else clicked.copy(
            direction = SortDirection.UNSPECIFIED
        )
    }

}

fun <T> and(l1: List<T>, l2: List<T>): Boolean {
    for (item1 in l1) {
        for (item2 in l2) {
            if (item1 == item2) return true
        }
    }
    return false
}