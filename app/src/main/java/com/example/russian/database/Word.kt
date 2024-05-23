package com.example.russian.database

import androidx.room.AutoMigration
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Word(
    @ColumnInfo("value")
    val value: String,

    @ColumnInfo("topic")
    val topic: Int,
    @ColumnInfo(name = "percentage")
    val percentage: Float
){

    constructor(
        id: Int,
        value: String,
        topic: Int,
        right: Int,
        attempts: Int,
        percentage: Float,

    ) : this(
        value,
        topic,
        percentage
    ){
        this.id = id
        this.gotItRight = right
        this.attempts = attempts
    }

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "right")
    var gotItRight = 0

    @ColumnInfo(name = "attempts")
    var attempts = 0


}