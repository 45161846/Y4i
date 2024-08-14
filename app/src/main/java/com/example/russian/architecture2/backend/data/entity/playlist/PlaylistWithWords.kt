package com.example.russian.architecture2.backend.data.entity.playlist

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.russian.architecture2.backend.data.entity.NewWord


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