package com.example.russian.game.back.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.russian.main.Id

@Entity
data class YdarEntity(
    @PrimaryKey(autoGenerate = false) val taskId: Id,
    val taskValue: String
)