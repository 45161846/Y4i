package com.example.russian.architecture2.backend.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.russian.architecture.data.olddata.entity.Spelling


@Entity
data class Spelling(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "spellingId")
    var id: Long = 0,

    @ColumnInfo(name = "wordId")
    var wordId: Long,

    @ColumnInfo(name = "value")
    var value: String,

    @ColumnInfo(name = "isCorrect")
    var isCorrect: Boolean
)

data class WordWithSpellings(
    @Embedded val word: NewWord,
    @Relation(
        parentColumn = "id",
        entityColumn = "wordId"
    )
    val spellings: List<Spelling>
)