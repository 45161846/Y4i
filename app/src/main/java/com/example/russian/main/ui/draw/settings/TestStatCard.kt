package com.example.russian.main.ui.draw.settings

import kotlinx.coroutines.flow.MutableStateFlow


data class TestStatsCardState(
    val winrate: MutableStateFlow<Double>,
    val showIndicator: MutableStateFlow<Boolean>,
    val showWinrate: MutableStateFlow<Boolean>,
    val showTypeIcon: MutableStateFlow<Boolean>
)
