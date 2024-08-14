package com.example.russian.architecture2.backend.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.russian.architecture.data.olddata.entity.Spelling


@Entity
class NewWord (
    @ColumnInfo("value")
    val value: String,

    @ColumnInfo("topic")
    val topic: Int,
){

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("wordId")
    var id: Long = 0


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