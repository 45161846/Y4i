package com.example.russian.main.back.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.russian.architectured.Id


@Entity
data class SpellingVariant(
    @PrimaryKey(autoGenerate = true)
    var id: Id = Id(0L),

    val partOfTaskId: Id = Id(0L),
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