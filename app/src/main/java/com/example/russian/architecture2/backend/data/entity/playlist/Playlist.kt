package com.example.russian.architecture2.backend.data.entity.playlist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "new-playlist")
data class Playlist(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("playlistId")
    var id: Long = 0,

    val title: String,

    val capacity: Long,
)