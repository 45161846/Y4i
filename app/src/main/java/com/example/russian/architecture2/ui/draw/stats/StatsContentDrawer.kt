package com.example.russian.architecture2.ui.draw.stats

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.russian.MyEnumClasses.ExceptionsTexts
import com.example.russian.R
import com.example.russian.architecture2.ui.draw.test.testStatsState
import com.example.russian.architecture2.ui.state.StatCardUIState
import com.example.russian.architecture2.ui.state.StatsFirstScreenState
import com.example.russian.ui.theme.OnSecondary1
import com.example.russian.ui.theme.family

@Composable
fun DrawLoading(paddingValues: PaddingValues) { //TODO add shimmer
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = myModifier(paddingValues = paddingValues)
    ) {
        Text(
            text = "loading...",
            color = OnSecondary1,
            fontSize = 30.sp
        )
    }
}

@Composable
fun DrawNoWordsFound(
    paddingValues: PaddingValues
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = myModifier(paddingValues = paddingValues)
    ) {
        Text(
            text = ExceptionsTexts().NO_WORDS_FOUND(),
            color = OnSecondary1,
            fontSize = 30.sp
        )
    }
}

@Composable
fun DrawStatContent(
    contentListState: StatsFirstScreenState,
    paddingValues: PaddingValues,
    listState: LazyListState
) {

    when(contentListState){
        is StatsFirstScreenState.Success -> {
            LazyColumn(
                modifier = myModifier(paddingValues),
                state = listState
            ) {
                items(items = contentListState.words) {
                    CardOfStats(it)
                }
            }
        }
        is StatsFirstScreenState.Loading -> DrawLoading(paddingValues)
        is StatsFirstScreenState.NothingFound -> DrawNoWordsFound(paddingValues)
    }


}


@Composable
private fun myModifier(paddingValues: PaddingValues): Modifier {
    return Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.dark_background))
        .padding(paddingValues)
}

@SuppressLint("DefaultLocale")
@Composable
private fun CardOfStats(state: StatCardUIState) {

    val displayableText = state.text
    val winRate = state.winRate

    val backColor = state.backgroundColor

    Row(
        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp, 3.dp, 8.dp, 3.dp)
            .background(
                backColor,
                RoundedCornerShape(5.dp)
            )
    ) {
        Text(
            text = displayableText,
            fontSize = 20.sp,
            color = state.textColor,
            fontFamily = family,
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 10.dp)
                .width(200.dp)
        )

        Spacer(
            modifier = Modifier.weight(1F)
        )

        if (state.showWinRateText) {
            if (winRate >= 0) {
                Text(
                    color = calculateColor(winRate),
                    text = (winRate * 100).toInt().toString() + "%",
                    modifier = Modifier
                        .padding(10.dp, 0.dp)
                )

            }
        }

        if (state.showWinRateIndicator) {
            Spacer(
                modifier = Modifier
                    .background(calculateColor(winRate), RoundedCornerShape(100))
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

@Preview
@Composable
private fun StatsListPreview() {
    DrawStatContent(
        contentListState = testStatsState(),
        PaddingValues(),
        rememberLazyListState()
    )
}