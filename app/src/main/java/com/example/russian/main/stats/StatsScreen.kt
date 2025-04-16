package com.example.russian.main.stats

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.russian.R
import com.example.russian.main.Id
import com.example.russian.main.custom.FullScreenColumn
import com.example.russian.main.prac.details.ShimmerStats
import com.example.russian.main.settings.StatDisplaySetting
import com.example.russian.main.stats.comp.stats.BottomBarState
import com.example.russian.main.stats.comp.stats.DrawToTopButton
import com.example.russian.main.stats.comp.stats.StatSnackBar
import com.example.russian.main.stats.comp.stats.StatsScreenState
import com.example.russian.main.stats.comp.stats.TaskCardUiState
import com.example.russian.main.theme.LightGreen
import com.example.russian.main.theme.LightRed
import com.example.russian.main.theme.RussianTheme
import com.example.russian.main.util.contrastPortionedColor
import com.example.russian.main.util.contrastText
import com.example.russian.main.util.maximizeBrightness
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun StatsScreen(
    viewModel: StatsViewModel,
    changeBottomBarVisibility: (BottomBarState) -> Unit
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    var taskIdMessage by remember {
        //ноль не менять, все сломается
        //положительные значения означают вызов для задания
        //с таким id
        mutableStateOf(Id(0))
    }
    var snackText by remember {
        mutableStateOf("")
    }
    val snackTextAdd = context.getString(R.string.toast_add_to_favorite)
    val snackTextRemove = context.getString(R.string.toast_remove_from_favorite)


    state.let { screenState ->
        when (screenState) {
            is StatsScreenState.Loading -> DrawLoading(screenState.displaySetting)
            is StatsScreenState.UI -> DrawStatContent(
                screenState,
                changeBottomBarVisibility = changeBottomBarVisibility
            ) { id, favorite ->
                viewModel.addToFavorite(id)
                snackText = if (favorite) snackTextRemove else snackTextAdd
                taskIdMessage = id
            }
        }
    }

    StatSnackBar(
        taskIdMessage,
        snackText,
        1000
    )

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
    listState: LazyListState = rememberLazyListState(),
    changeBottomBarVisibility: (BottomBarState) -> Unit,
    onMarkTask: (Id, Boolean) -> Unit
) {

    val coroutineScope = rememberCoroutineScope()

    val cardModifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(8.dp, 3.dp, 8.dp, 3.dp)
        .background(
            MaterialTheme.colorScheme.surfaceVariant,
            RoundedCornerShape(5.dp)
        )

    val showBottom by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex < 10
        }
    }

    changeBottomBarVisibility(BottomBarState.valueOf(showBottom))

    FullScreenColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        state = listState
    ) {
        items(items = contentListState.tasks) {
            CardOfStats(
                it,
                cardModifier
            ) {
                onMarkTask(it.taskId, it.isFavorite)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeGestures),
        contentAlignment = Alignment.BottomEnd
    ) {
        DrawToTopButton(
            listState,
            showBottom,
            modifier = Modifier
                .padding(bottom = 16.dp, end = 16.dp)
        ) {
            coroutineScope.launch {
                listState.scrollToItem(0)
            }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("DefaultLocale")
@Composable
fun CardOfStats(
    state: TaskCardUiState,
    modifier: Modifier,
    onLongClick: () -> Unit
) {

    var isFavorite by remember(state.isFavorite) {
        mutableStateOf(state.isFavorite)
    }

    val defaultBackColor = MaterialTheme.colorScheme.surfaceVariant

    val backColor by animateColorAsState(
        if (isFavorite) MaterialTheme.colorScheme.inversePrimary
//            contrastPortionedColor(
//            worstColor = defaultBackColor,
//            bestColor = defaultBackColor.invert(),
//            0.85
//        ).maximizeBrightness()
        else defaultBackColor, label = ""
    )

    val textColor = backColor.contrastText()

    val displayableText = state.text
    val color = if (state.hasBeenAnswered) {
        contrastPortionedColor(
            worstColor = LightRed,
            bestColor = LightGreen,
            state.winRate
        ).maximizeBrightness()
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.33f)
    }

    Row(
        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(
                backColor,
                RoundedCornerShape(4.dp)
            )
            .combinedClickable(
                onClick = {

                },
                onLongClick = {
                    onLongClick()
                    isFavorite = isFavorite.not()
                }
            )
    ) {
        Text(
            text = displayableText,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = textColor.copy(alpha = 0.87f)
            ),
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
                    .size(32.dp),
                tint = textColor.copy(alpha = 0.6f)
            )
        }


        if (state.params.showWinrate) {

            val text = if (state.hasBeenAnswered) {
                "${(state.winRate * 100).toInt()}%"
            } else {
                "--%"
            }

            Text(
                color = textColor.copy(alpha = 0.6f),
                text = text,
                modifier = Modifier
                    .padding(10.dp, 0.dp)
            )

        }


        if (state.params.showIndicator) {
            Spacer(
                modifier = Modifier
                    .background(color, RoundedCornerShape(100))
                    .size(10.dp)
                    .border(
                        (0.5).dp,
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(
                            alpha = 0.34f
                        ),
                        RoundedCornerShape(100)
                    )
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

    RussianTheme {
        DrawStatContent(
            contentListState = StatsScreenState.UI(
                List(20) {

                    val a = Random.nextInt(0, 5)
                    val b = Random.nextInt(0, 5)
                    val winrate = a.toDouble() / (a + b).toDouble()

                    TaskCardUiState(
                        taskId = Id(it.toLong()),
                        text = "Word $it",
                        winRate = winrate,
                        hasBeenAnswered = (a + b) > 0,
                        themeIconId = R.drawable.ydar_icon,
                        StatDisplaySetting(true, true, true),
                        isFavorite = (it % 3) == 1
                    )
                }
            ),
            rememberLazyListState(), {}
        ) { _, _ ->

        }
    }
}