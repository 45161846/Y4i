package com.example.russian.architecture2.ui.state

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.graphics.Color
import com.example.russian.architecture2.backend.data.entity.WordStatistics

sealed class StatsScreenState() {

    data object Loading : StatsScreenState()

    data object NothingFound: StatsScreenState()

    data class Success(
        val words: List<StatCardUIState>,
        val backgroundColor: Color,
        val onSearch: (String) -> Unit,
    ): StatsScreenState()

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
