package com.example.russian.main.back.data.entity.playlist

import androidx.room.Entity


@Entity(primaryKeys = ["playlistId", "taskId"])
data class PlaylistCrossRef (
    val playlistId: Long,
    val taskId: Long
)