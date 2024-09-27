package com.example.russian.ui.draw.test

import androidx.compose.ui.graphics.Color
import com.example.russian.R
import com.example.russian.ui.state.StatCardUIState
import com.example.russian.ui.state.StatsFirstScreenState
import com.example.russian.ui.theme.OnSecondary2
import com.example.russian.ui.theme.PrimaryBackground
import com.example.russian.ui.theme.ThirdBackground
import com.example.russian.viewmodel.main.StatsParametersAPI


fun testStatsState(): StatsFirstScreenState.Success{

    val wordsData = testDataWordsList()

    val statsState = List(wordsData.size){
        val word = wordsData[it]

        StatCardUIState(
            word.stats.displayableText,
            word.stats.winRate(),
            R.drawable.ic_launcher_foreground,
            object : StatsParametersAPI{
                override fun showWinRate(): Boolean = true

                override fun showWinRateIndicator(): Boolean = true

                override fun showIcon(): Boolean  = true
            },
            ThirdBackground,
            OnSecondary2
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