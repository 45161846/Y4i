package com.example.russian.main.back.data.entity.playlist

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.russian.architectured.Id


@Entity
data class PlaylistPositions(
    @PrimaryKey
    val playlistId: Id,

    val positionIndex: Int
)