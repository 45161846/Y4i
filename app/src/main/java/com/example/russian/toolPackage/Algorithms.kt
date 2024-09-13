package com.example.russian.toolPackage

import com.example.russian.architecture2.backend.data.entity.playlist.Playlist
import com.example.russian.architecture2.ui.state.MarkedPlaylist


/*
returns marked list without changing existing old data
 */
fun mergeOldNewPlaylist(old: List<MarkedPlaylist>, new: List<Playlist>): List<MarkedPlaylist> {
    return new.map {newPlaylist ->
        var marked = true
        old.forEach { oldPlaylist ->
            if (compare(oldPlaylist.playlist, newPlaylist)) {
                marked = oldPlaylist.marked
            }
        }
        MarkedPlaylist(newPlaylist, marked)
    }
}

private fun compare(playlist1: Playlist, playlist2: Playlist): Boolean {
    return playlist1.id == playlist2.id && playlist1.title == playlist2.title
}