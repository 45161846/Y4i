package com.example.russian.main.back.data.entity.playlist

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class PlaylistPositions(
    @PrimaryKey
    val playlistId: Long,

    val positionIndex: Int
)