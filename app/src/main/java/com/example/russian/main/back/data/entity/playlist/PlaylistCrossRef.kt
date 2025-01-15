package com.example.russian.main.back.data.entity.playlist

import androidx.room.Entity
import com.example.russian.architectured.Id


@Entity(primaryKeys = ["playlistId", "taskId"])
data class PlaylistCrossRef (
    val playlistId: Id = Id(0),
    val taskId: Id = Id(0)
)