package com.example.russian.ui.draw.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.russian.ui.theme.PrimaryBackground
import com.example.russian.ui.theme.SecondaryBackground
import com.example.russian.ui.theme.ThirdBackground
import com.example.russian.ui.theme.family
import kotlinx.coroutines.flow.MutableStateFlow



@Composable
fun TestStatsCard(state: TestStatsCardState){

    val displayableText = "Тестовое слово"
    val winRate = state.winrate.collectAsState()
    val showWinrate = state.showWinrate.collectAsState()
    val showIndicator = state.showIndicator.collectAsState()

    val backColor = ThirdBackground

    Row(
        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                backColor,
                RoundedCornerShape(5.dp)
            )
    ) {
        Text(
            text = displayableText,
            fontSize = 20.sp,
            color = Color.White,
            fontFamily = family,
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 10.dp)
                .width(200.dp)
        )

        Spacer(
            modifier = Modifier.weight(1F)
        )

        if (showWinrate.value) {
            if (winRate.value >= 0) {
                Text(
                    color = calculateColor(winRate.value),
                    text = (winRate.value * 100).toInt().toString() + "%",
                    modifier = Modifier
                        .padding(10.dp, 0.dp)
                )

            }
        }

        if (showIndicator.value) {
            Spacer(
                modifier = Modifier
                    .background(calculateColor(winRate.value), RoundedCornerShape(100))
                    .size(70.dp, 5.dp)
            )
            Spacer(
                modifier = Modifier
                    .background(Color.Transparent)
                    .size(10.dp, 2.dp)
            )
        }
    }

}

private fun calculateColor(winRate: Double): Color {
    if (winRate < 0) {
        return Color(152, 152, 152)
    }
    return Color(((1.0 - winRate) * 2).toFloat(), (winRate * 2).toFloat(), 0F, alpha = 1F)
}

data class TestStatsCardState(
    val winrate: MutableStateFlow<Double>,
    val showIndicator: MutableStateFlow<Boolean>,
    val showWinrate: MutableStateFlow<Boolean>,
    val showTypeIcon: MutableStateFlow<Boolean>
)
