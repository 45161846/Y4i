package com.example.russian.back.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity
data class SpellingVariant(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    val partOfTaskId: Long,
    val value: String,
    val correct: Boolean
)

data class PartOfTaskWithSpellingVariants(
    @Embedded val partOfTask: PartOfTask,

    @Relation(
        parentColumn = "PartOfTaskId",
        entityColumn = "partOfTaskId"
    )
    val spellings: List<SpellingVariant>
)