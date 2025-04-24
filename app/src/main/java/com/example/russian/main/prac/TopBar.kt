package com.example.russian.main.prac

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PracTopBar(
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    modifier: Modifier = Modifier,
) {

    var startLocal by remember {
        mutableIntStateOf(0)
    }

    var startRemote by remember {
        mutableIntStateOf(0)
    }

    var localLength by remember {
        mutableStateOf<Int?>(null)
    }
    var remoteLength by remember {
        mutableStateOf<Int?>(null)
    }
    var lineLength by remember {
        mutableIntStateOf(0)
    }

    val offset by animateIntOffsetAsState(
        targetValue = if (pagerState.currentPage == 1) {
            IntOffset(startRemote, 0)
        } else {
            IntOffset(startLocal, 0)
        }, label = "offset"
    )

    val textStyle = MaterialTheme.typography.titleMedium
        .copy(color = MaterialTheme.colorScheme.onSurfaceVariant)

    Column(
        modifier
            .fillMaxWidth()
            .padding(8.dp)
        , verticalArrangement = Arrangement.Center
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Box(modifier = Modifier
                .weight(1F)
                .padding(horizontal = 24.dp)
                .background(Color.Transparent)
                .clickable(
                    interactionSource = null, indication = null
                ) {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                    lineLength = localLength ?: 0
                }) {
                Text(
                    "Local",
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coord ->
                            startLocal = coord.positionInRoot().x.toInt()
                            if (localLength == null) {
                                lineLength = coord.size.width
                                localLength = coord.size.width
                            }
                            localLength = coord.size.width
                        },
                    style = textStyle,
                    textAlign = TextAlign.Center
                )
            }

            Box(modifier = Modifier
                .weight(1F)
                .padding(horizontal = 24.dp)
                .background(Color.Transparent)
                .clickable(
                    interactionSource = null, indication = null
                ) {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                    remoteLength?.let {
                        lineLength = it
                    } ?: 0
                }
            ) {
                Text(
                    "Remote",
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coord ->
                            startRemote = coord.positionInRoot().x.toInt()
                            remoteLength = coord.size.width
                        },
                    style = textStyle,
                    textAlign = TextAlign.Center
                )
            }
        }


        Spacer(
            Modifier
                .offset {
                    offset
                }
                .animateContentSize()
                .size(
                    width = (lineLength.toFloat() / LocalDensity.current.density).dp, height = 5.dp
                )
                .background(
                    MaterialTheme.colorScheme.onSecondary,
                    RoundedCornerShape(100)
                )
        )
    }
}