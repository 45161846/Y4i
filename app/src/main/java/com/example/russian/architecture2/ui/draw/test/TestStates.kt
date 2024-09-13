package com.example.russian.architecture2.ui.draw.test

import androidx.compose.ui.graphics.Color
import com.example.russian.architecture2.ui.state.StatCardUIState
import com.example.russian.architecture2.ui.state.StatsFirstScreenState
import com.example.russian.ui.theme.OnSecondary2
import com.example.russian.ui.theme.PrimaryBackground
import com.example.russian.ui.theme.ThirdBackground


fun testStatsState(): StatsFirstScreenState.Success{

    val wordsData = testDataWordsList()

    val statsState = List(wordsData.size){
        val word = wordsData[it]

        StatCardUIState(
            word.stats.displayableText,
            word.stats.winRate(),
            true,
            false,
            ThirdBackground,
            OnSecondary2,
            calculateColor(word.stats.winRate())
        )
    }

    return StatsFirstScreenState.Success(
        statsState,
        PrimaryBackground,
        {}
    )
}

private fun calculateColor(winRate: Double): Color {
    if (winRate < 0) {
        return Color(152, 152, 152)
    }
    return Color(((1.0 - winRate) * 2).toFloat(), (winRate * 2).toFloat(), 0F, alpha = 1F)
}