package com.example.russian.back.data.entity.playlist

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.russian.back.data.entity.NewWord


data class PlaylistWithWords (
    @Embedded
    val playlist: Playlist,

    @Relation(
        parentColumn = "playlistId",
        entityColumn = "wordId",
        associateBy = Junction(PlaylistCrossRef::class)
    )
    val words: List<NewWord>
)