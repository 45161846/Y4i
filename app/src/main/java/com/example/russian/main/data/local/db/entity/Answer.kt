package com.example.russian.main.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.russian.main.Id
import com.example.russian.main.Time


@Entity
data class Answer(
    @PrimaryKey(autoGenerate = true)
    var id: Id = Id(0),
    val taskId: Id,

    val correct: Boolean,
    val time: Time? = null,
    val solveTime: Time? = null

)
