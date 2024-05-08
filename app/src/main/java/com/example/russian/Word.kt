package com.example.russian

import androidx.room.AutoMigration
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Word(
    val v: String,
    val taskTopic: Int
){

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo
    var topic: Int = taskTopic

    @ColumnInfo
    var value: String = v

    @ColumnInfo(name = "right")
    var gotItRight = 0

    @ColumnInfo(name = "wrong")
    var gotItWrong = 0

    @ColumnInfo(name = "percentage")
    var percentage: Float = if((gotItWrong + gotItRight) != 0){
        gotItRight.toFloat()/(gotItWrong + gotItRight).toFloat()
    }else{
        0F
    }

}