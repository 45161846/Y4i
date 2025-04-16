package com.example.russian.main.util

import androidx.room.TypeConverter
import com.example.russian.game.back.data.entity.Statistics
import com.example.russian.main.TaskType
import com.example.russian.main.iconId
import com.example.russian.main.settings.StatDisplaySetting
import com.example.russian.main.stats.comp.stats.TaskCardUiState

class Converters {
    @TypeConverter
    fun toTaskType(value: String) = enumValueOf<TaskType>(value)

    @TypeConverter
    fun fromTaskType(value: TaskType) = value.name
}

fun Statistics.toCardUiState(params: StatDisplaySetting) = TaskCardUiState(
    text = displayableText,
    winRate = correct.toDouble() / (attempts).toDouble(),
    hasBeenAnswered = attempts > 0,
    themeIconId = type.iconId(),
    params = params,
    taskId = taskId,
    isFavorite = favorite
)