package com.example.russian.architecture.data.olddata.entity

import androidx.room.Entity


@Entity(primaryKeys = ["wordId", "playlistId"])
data class PlaylistWordCrossRef(
    val wordId: Int,
    val playlistId: Int
)