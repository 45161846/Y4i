package com.example.russian.ui.draw.stats

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.russian.R
import com.example.russian.enums.ExceptionsTexts
import com.example.russian.ui.actions.MyActions
import com.example.russian.ui.draw.settings.TestStatsCardState
import com.example.russian.ui.draw.test.testStatsState
import com.example.russian.ui.state.StatCardUIState
import com.example.russian.ui.state.StatsFirstScreenState
import com.example.russian.ui.theme.OnSecondary1
import com.example.russian.ui.theme.ThirdBackground
import com.example.russian.ui.theme.family

@Composable
fun DrawLoading(paddingValues: PaddingValues) { //TODO add shimmer
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_background))
            .padding(paddingValues)
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
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_background))
            .padding(paddingValues)
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

    val cardModifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(8.dp, 3.dp, 8.dp, 3.dp)
        .background(
            ThirdBackground,
            RoundedCornerShape(5.dp)
        )

    when (contentListState) {
        is StatsFirstScreenState.Success -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.dark_background))
                    .padding(paddingValues),
                state = listState
            ) {
                items(items = contentListState.words) {
                    CardOfStats(it, cardModifier)
                }
            }
        }

        is StatsFirstScreenState.Loading -> DrawLoading(paddingValues)
        is StatsFirstScreenState.NothingFound -> DrawNoWordsFound(paddingValues)
    }


}

@SuppressLint("DefaultLocale")
@Composable
fun CardOfStats(
    state: StatCardUIState,
    modifier: Modifier
) {

    val displayableText = state.text
    val winRate = state.winRate
    val color by remember(winRate) {
        mutableStateOf(com.example.russian.ui.state.calculateColor(winRate))
    }

    Row(
        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = displayableText,
            fontSize = 20.sp,
            color = state.textColor,
            fontFamily = family,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 10.dp)
                .weight(1F)
        )

        if (state.params.showIcon()) {
            Icon(
                ImageVector.vectorResource(state.themeIconId), null,
                modifier = Modifier
                    .size(32.dp)
            )
        }

        if (state.params.showWinRate()) {

            val text = if (winRate < 0) {
                "--%"
            } else {
                "${(winRate * 100).toInt()}%"
            }

            Text(
                color = color,
                text = text,
                modifier = Modifier
                    .padding(10.dp, 0.dp)
            )

        }

        if (state.params.showWinRateIndicator()) {
            Spacer(
                modifier = Modifier
                    .background(color, RoundedCornerShape(100))
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


@SuppressLint("DefaultLocale")
@Composable
fun TestCardOfStats(
    state: TestStatsCardState,
    modifier: Modifier
) {

    val displayableText = "Тестовое слово"

    val accurateWinrate = state.winrate.collectAsState()

    val winRate by remember((accurateWinrate.value * 100).toInt()) {
        mutableIntStateOf((accurateWinrate.value * 100).toInt())
    }

    val color by remember(winRate) {
        mutableStateOf(com.example.russian.ui.state.calculateColor(winRate.toDouble() / 100))
    }

    Row(
        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
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

        if (state.showTypeIcon.collectAsState().value) {
            Icon(
                ImageVector.vectorResource(R.drawable.ic_launcher_foreground), null,
                modifier = Modifier
                    .size(32.dp)
            )
        }

        if (state.showWinrate.collectAsState().value) {
            Text(
                color = color,
                text = "$winRate%",
                modifier = Modifier
                    .padding(5.dp, 0.dp)
            )
        }

        if (state.showIndicator.collectAsState().value) {
            Spacer(
                modifier = Modifier
                    .background(color, RoundedCornerShape(100))
                    .size(60.dp, 5.dp)
            )
            Spacer(
                modifier = Modifier
                    .background(Color.Transparent)
                    .size(5.dp, 2.dp)
            )
        }
    }
}


data class StatsScreenActions(
    val search: (String) -> Unit
) : MyActions()

@Preview
@Composable
private fun StatsListPreview() {
    DrawStatContent(
        contentListState = testStatsState(),
        PaddingValues(),
        rememberLazyListState()
    )
}