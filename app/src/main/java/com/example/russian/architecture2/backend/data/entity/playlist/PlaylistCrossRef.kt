package com.example.russian.architecture2.backend.data.entity.playlist

import androidx.room.Entity


@Entity(primaryKeys = ["playlistId", "wordId"])
data class PlaylistCrossRef (
    val playlistId: Long,
    val wordId: Long
)