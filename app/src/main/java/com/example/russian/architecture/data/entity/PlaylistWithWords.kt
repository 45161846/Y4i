package com.example.russian.architecture.data.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation


class PlaylistWithWords ( // описывает формат того, как достаются из БД слова в плейлисте
    @Embedded val playlist: Playlist,
    @Relation(
        parentColumn = "playlistId",
        entityColumn = "wordId",
        associateBy = Junction(PlaylistWordCrossRef::class)
    )
    val words: List<Word>
)