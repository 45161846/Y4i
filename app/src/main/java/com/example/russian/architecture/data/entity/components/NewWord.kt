package com.example.russian.architecture.data.entity.components

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class NewWord (
    @ColumnInfo("value")
    val value: String,

    @ColumnInfo("topic")
    val topic: Int,
){

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("new-wordId")
    var id: Int = 0


    override fun toString(): String {
        return "Word id: $id. Value: $value"
    }
}