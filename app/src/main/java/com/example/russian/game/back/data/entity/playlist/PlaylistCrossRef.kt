package com.example.russian.game.back.data.entity.playlist

import androidx.room.Entity
import androidx.room.Index
import com.example.russian.main.Id


@Entity(
    primaryKeys = ["playlistId", "taskId"],
    indices = [
        Index(value = ["taskId"]),
        Index(value = ["playlistId"]),
    ]
)
data class PlaylistCrossRef (
    val playlistId: Id = Id(0),
    val taskId: Id = Id(0)
)