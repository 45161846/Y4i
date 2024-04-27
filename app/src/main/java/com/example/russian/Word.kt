package com.example.russian

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Word(@PrimaryKey @ColumnInfo(name = "value") val v: String) {

    val value: String
    @ColumnInfo(name = "right")
    var gotItRight = 0
    @ColumnInfo(name = "wrong")
    var gotItWrong = 0
    @ColumnInfo(name = "percentage")
    var percentage = if((gotItWrong + gotItRight) != 0){
        gotItRight/(gotItWrong + gotItRight)
    }else{
        0F
    }

    init {
        value = v
    }

}