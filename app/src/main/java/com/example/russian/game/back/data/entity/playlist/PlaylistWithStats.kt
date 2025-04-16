package com.example.russian.game.back.data.entity.playlist

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.russian.game.back.data.entity.Statistics

data class PlaylistWithStats(
    @Embedded
    val playlist: Playlist,

    @Relation(
        parentColumn = "playlistId",
        entityColumn = "taskId",
        associateBy = Junction(PlaylistCrossRef::class)
    )
    val words: List<Statistics>
)