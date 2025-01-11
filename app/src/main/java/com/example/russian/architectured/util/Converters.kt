package com.example.russian.architectured.util

import androidx.room.TypeConverter
import com.example.russian.architectured.TaskType

class Converters {

    @TypeConverter
    fun toTaskType(value: String) = enumValueOf<TaskType>(value)

    @TypeConverter
    fun fromTaskType(value: TaskType) = value.name

}