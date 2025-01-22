package com.example.russian.game.back.data.entity.playlist

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.russian.game.back.data.entity.Statistics
import com.example.russian.main.settings.StatDisplaySetting
import com.example.russian.main.stats.comp.stats.TaskCardUiState
import com.example.russian.main.util.toCardUiState

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