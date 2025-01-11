package com.example.russian.architectured.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.russian.architectured.Id
import com.example.russian.architectured.Time


@Entity
data class Answer(
    @PrimaryKey(autoGenerate = true)
    val id: Id,
    val taskId: Id,

    val correct: Boolean,
    val time: Time? = null,
    val solveTime: Time? = null

)
