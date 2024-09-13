package com.example.russian.architecture2.ui.state

import androidx.compose.ui.graphics.Color

sealed class StatsFirstScreenState(
    open val onSearch: (String) -> Unit
) {

    data object Loading : StatsFirstScreenState({})

    data class NothingFound(
        override val onSearch: (String) -> Unit
    ): StatsFirstScreenState(onSearch)

    data class Success(
        val words: List<StatCardUIState>,
        val backgroundColor: Color,
        override val onSearch: (String) -> Unit,
    ): StatsFirstScreenState(onSearch)

}

data class StatCardUIState(
    val text: String,
    val winRate: Double,
    val showWinRateText: Boolean,
    val showWinRateIndicator: Boolean,
    val backgroundColor: Color,
    val textColor: Color,
    val winRateColor: Color
)
