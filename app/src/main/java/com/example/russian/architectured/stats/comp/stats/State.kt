package com.example.russian.architectured.stats.comp.stats

import com.example.russian.main.ui.draw.settings.StatDisplaySetting

sealed class StatsScreenState{

    data object Loading: StatsScreenState()

    data class UI(
        val tasks: List<CardUIData>,
    ) : StatsScreenState()

}

data class CardUIData(
    val text: String,
    val winRate: Double,
    val hasBeenAnswered: Boolean,
    val themeIconId: Int,
    val params: StatDisplaySetting,
)

data class StatsParams(
    val showPercent: Boolean,
    val showLine: Boolean,
    val showIcon: Boolean
)

data class Filter(
    val showUnanswered: Boolean
)