package com.example.russian.architecture.data.olddata.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity
data class Spelling(

    @PrimaryKey(autoGenerate = true)
    var spellingId: Int = 0,

    @ColumnInfo(name = "wordId")
    var wordId: Int,

    @ColumnInfo(name = "value")
    var value: String,

    @ColumnInfo(name = "isCorrect")
    var isCorrect: Boolean
){
    override fun equals(other: Any?): Boolean {

        if(other !is Spelling) {
            return false
        }

        return isCorrect == other.isCorrect && value == other.value
    }

    override fun hashCode(): Int {
        var result = spellingId
        result = 31 * result + wordId
        result = 31 * result + value.hashCode()
        result = 31 * result + isCorrect.hashCode()
        return result
    }
}

@Entity
data class WordWithSpellings(
    @Embedded val word: Word,
    @Relation(
        parentColumn = "wordId",
        entityColumn = "wordId"
    )
    val spellings: List<Spelling>
)

