package com.example.russian.game.back.data.entity.playlist

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.russian.main.Id


@Entity
data class PlaylistPositions(
    @PrimaryKey
    val playlistId: Id,

    val positionIndex: Int
)