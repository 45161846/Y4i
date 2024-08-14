package com.example.russian.architecture.data.olddata.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity()
data class Playlist(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("playlistId")
    val id: Int = 0,

    @ColumnInfo("title")
    val title: String,

    @ColumnInfo("capacity")
    val capacity: Int,
)

fun initialPlaylists() = listOf(
    Playlist(title = "Наречия ФИПИ", capacity = 0),
    Playlist(title = "Паронимы ФИПИ", capacity = 0),
    Playlist(title = "Ударения ФИПИ", capacity = 0)
)