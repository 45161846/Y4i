package com.example.russian.main.stats.comp.stats

import com.example.russian.main.Id
import com.example.russian.main.settings.StatDisplaySetting


sealed class StatsScreenState{

    data class Loading(
        val displaySetting: StatDisplaySetting
    ): StatsScreenState()

    data class UI(
        val tasks: List<TaskCardUiState>,
    ) : StatsScreenState()

}

data class TaskCardUiState(
    val taskId: Id,
    val text: String,
    val winRate: Double,
    val hasBeenAnswered: Boolean,
    val themeIconId: Int,
    val params: StatDisplaySetting,
    val isFavorite: Boolean
)