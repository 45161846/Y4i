package com.example.russian.back.data.entity.playlist

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class PlaylistPositions(
    @PrimaryKey
    val playlistId: Long,

    val positionIndex: Int
)