package com.example.russian.main.stats

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.russian.R
import com.example.russian.main.prac.details.ShimmerStats
import com.example.russian.main.settings.StatDisplaySetting
import com.example.russian.main.stats.comp.stats.StatsScreenState
import com.example.russian.main.stats.comp.stats.TaskCardUiState
import com.example.russian.main.util.contrastPortionedColor
import com.valentinilk.shimmer.shimmer
import kotlin.random.Random

@Composable
fun StatsScreen(
    viewModel: StatsViewModel,
    listState: LazyListState,
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    state.let {
        when (it) {
            is StatsScreenState.Loading -> DrawLoading(it.displaySetting)
            is StatsScreenState.UI -> DrawStatContent(
                it, listState
            )
        }
    }

}

@Composable
fun DrawLoading(
    displaySetting: StatDisplaySetting
) {

    val cardModifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(8.dp, 3.dp, 8.dp, 3.dp)
        .background(
            MaterialTheme.colorScheme.surfaceVariant,
            RoundedCornerShape(5.dp)
        )

    val shimmerModifier = cardModifier
        .shimmer()

    val lineHeightDp: Dp = with(LocalDensity.current) {
        MaterialTheme.typography.bodyMedium.fontSize.toDp()
    }

    val shimmerColor = MaterialTheme.colorScheme.onSurface.copy(
        alpha = 0.38F
    )

    val simmerShape = RoundedCornerShape(30)

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_background))
            .shimmer()
    ) {
        LazyColumn {
            items(30, { it }) {

                ShimmerStats(
                    displaySetting,
                    lineHeightDp,
                    shimmerColor,
                    simmerShape,
                    shimmerModifier
                )
            }
        }
    }
}


@Composable
fun DrawStatContent(
    contentListState: StatsScreenState.UI,
    listState: LazyListState = rememberLazyListState()
) {

    val cardModifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(8.dp, 3.dp, 8.dp, 3.dp)
        .background(
            MaterialTheme.colorScheme.surfaceVariant,
            RoundedCornerShape(5.dp)
        )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)

        ,
        state = listState
    ) {
        items(items = contentListState.tasks) {
            CardOfStats(it, cardModifier)
        }
    }

}

@SuppressLint("DefaultLocale")
@Composable
fun CardOfStats(
    state: TaskCardUiState,
    modifier: Modifier
) {

    val displayableText = state.text
    val color = contrastPortionedColor(
        if (state.hasBeenAnswered) state.winRate else -1.0
    )

    Row(
        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = displayableText,
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 10.dp)
                .weight(1F)
        )

        if (state.params.showTypeIcon) {
            Icon(
                ImageVector.vectorResource(state.themeIconId), null,
                modifier = Modifier
                    .size(32.dp), tint = Color.White
            )
        }


        if (state.params.showWinrate) {

            val text = if (state.hasBeenAnswered) {
                "${(state.winRate * 100).toInt()}%"
            } else {
                "--%"
            }

            Text(
                color = color,
                text = text,
                modifier = Modifier
                    .padding(10.dp, 0.dp)
            )

        }


        if (state.params.showIndicator) {
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


@Preview
@Composable
fun StatsPreview(
    paddingValues: PaddingValues = PaddingValues()
) {

    DrawStatContent(
        contentListState = StatsScreenState.UI(
            List(20) {

                val a = Random.nextInt(0, 5)
                val b = Random.nextInt(0, 5)
                val winrate = a.toDouble() / (a + b).toDouble()

                TaskCardUiState(
                    text = "Word $it",
                    winRate = winrate,
                    hasBeenAnswered = (a + b) > 0,
                    themeIconId = R.drawable.ydar_icon,
                    StatDisplaySetting(false, true, true)
                )
            }
        ),
        rememberLazyListState()
    )
}