package com.example.russian.back.data.entity.playlist

import androidx.room.Entity


@Entity(primaryKeys = ["playlistId", "wordId"])
data class PlaylistCrossRef (
    val playlistId: Long,
    val wordId: Long
)