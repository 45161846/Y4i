package com.example.russian.architecture2.backend.data.entity.playlist

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(primaryKeys = ["playlistId", "wordId"])
data class PlaylistCrossRef (
    val playlistId: Long,
    val wordId: Long
)