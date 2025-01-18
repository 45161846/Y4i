package com.example.russian.main.ui.draw.settings

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable


data class TestStatsCardState(
    val winrate: Double,
    val displaySetting: StatDisplaySetting
)

@Serializable
data class StatDisplaySetting(
    val showWinrate: Boolean,
    val showTypeIcon: Boolean,
    val showIndicator: Boolean
)