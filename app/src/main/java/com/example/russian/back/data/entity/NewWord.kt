package com.example.russian.back.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity
class NewWord (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("wordId")
    var id: Long = 0,

    @ColumnInfo("value")
    val value: String,

    @ColumnInfo("topic")
    val topic: Int,
){
    override fun toString(): String {
        return "Word id: $id. Value: $value"
    }
}

class WordTaskNoSpelling(
    @Embedded
    val word: NewWord,

    @Relation(
        entity = TaskData::class,
        parentColumn = "wordId",
        entityColumn = "wordId"
    )
    val taskData: TaskData
)

class WordTaskSpelling(
    @Embedded
    val wordTask: WordTaskNoSpelling,

    @Relation(
        parentColumn = "wordId",
        entityColumn = "wordId"
    )
    val spellings: List<Spelling>
)