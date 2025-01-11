package com.example.russian.main.ui.draw.common

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.example.russian.main.ui.theme.LightGrey69
import com.example.russian.main.ui.theme.White
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun MySwitch(
    modifier: Modifier = Modifier,
    isChecked: Int,
    onCheckedChange: (checked: Int) -> Unit
) {

    var showAnimation by remember {
        mutableStateOf(false)
    }

    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val marbleSize by remember(size) {
        mutableIntStateOf(size.height.div(1.5F).toInt())
    }

    val initialOffset = (size.height.div(2) - marbleSize.div(2f)).toInt()
    val yOffset by remember(size, marbleSize) {
        mutableIntStateOf(initialOffset)
    }

    var offset by remember {
        mutableStateOf(IntOffset(initialOffset, initialOffset))
    }

    val marblePadding = 4.dp.value
    val scope = rememberCoroutineScope()
    val swipeableState = rememberSwipeableState(isChecked)

    val targetColor = MaterialTheme.colorScheme.tertiary
    val emptyColor = LightGrey69
    val backgroundColor = if (showAnimation) {
        animateColorAsState(
            targetValue = if (swipeableState.currentValue != 0) targetColor else emptyColor,
            label = ""
        )
    } else {
        remember {
            mutableStateOf(if (swipeableState.currentValue != 0) targetColor else emptyColor)
        }

    }
    val sizePx = size.width.minus(marbleSize + marblePadding.times(2))
    val anchors = mapOf(0f to 0, (sizePx - 1f) to 1)
    LaunchedEffect(key1 = swipeableState.currentValue, block = {
        onCheckedChange.invoke(swipeableState.currentValue)
        offset = IntOffset(
            x = (swipeableState.offset.value).roundToInt(),
            y = yOffset
        )
    })

    val doOnClick = {
        val target = if (swipeableState.currentValue == 0) 1 else 0

        scope.launch {

            if (showAnimation) {
                swipeableState.animateTo(
                    target,
                    anim = tween(150, easing = LinearEasing)
                )
            } else {
                swipeableState.animateTo(
                    1,
                    tween(0)
                )
            }

        }
    }

    Box(
        modifier = modifier
            .aspectRatio(2f)
            .clip(CircleShape)
            .clickable {

                doOnClick()

            }
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                enabled = false, //because you need to disable swipe
                orientation = Orientation.Horizontal
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        doOnClick()
                    }
                )
            }
            .background(backgroundColor.value)
            .onSizeChanged {
                size = it
            }

    ) {
        Box(
            modifier = Modifier
                .padding(start = (marblePadding / 2).dp, end = (marblePadding / 2).dp)
                .offset {
                    offset
                }
                .size(with(LocalDensity.current) { marbleSize.toDp() })
                .clip(CircleShape)
                .background(White)
                .clickable {
                    doOnClick()
                }
        )
    }
}

@Composable
@Preview
private fun Preview() {
    MySwitch(Modifier.fillMaxSize(), 1, {})
    MySwitch(Modifier.fillMaxWidth(), 0, {})
}