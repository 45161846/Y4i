package com.example.russian.ui.state

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.russian.viewmodel.main.StatsParametersAPI
import kotlinx.coroutines.flow.MutableStateFlow

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
    val themeIconId: Int,
    val params: StatsParametersAPI,
    val backgroundColor: Color,
    val textColor: Color
)


fun calculateColor(winRate: Double): Color {
    if (winRate < 0) {
        return Color(152, 152, 152)
    }
    return Color(((1.0 - winRate) * 2).toFloat(), (winRate * 1.5).toFloat(), 0.20F, alpha = 1F)
}