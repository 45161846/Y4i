package com.example.russian.ui.draw.common

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
import com.example.russian.ui.theme.FiltersScreenButtonActive
import com.example.russian.ui.theme.OnSecondary2
import com.example.russian.ui.theme.OnSecondary3
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun MySwitch(
    modifier: Modifier = Modifier,
    isChecked: Int,
    onCheckedChange: (checked: Int) -> Unit
) {

    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val marbleSize by remember(size) {
        mutableIntStateOf(size.height.div(1.5F).toInt())
    }
    val yOffset by remember(size, marbleSize) {
        mutableIntStateOf((size.height.div(2) - marbleSize.div(2f)).toInt())
    }

    val marblePadding = 4.dp.value
    val scope = rememberCoroutineScope()
    val swipeableState = rememberSwipeableState(isChecked)

    val backgroundColor = animateColorAsState(
        targetValue = if (swipeableState.currentValue != 0) FiltersScreenButtonActive else OnSecondary3,
        label = ""
    )
    val sizePx = size.width.minus(marbleSize + marblePadding.times(2))
    val anchors = mapOf(0f to 0, (sizePx - 1f) to 1)
    LaunchedEffect(key1 = swipeableState.currentValue, block = {
        onCheckedChange.invoke(swipeableState.currentValue)
    })
    Box(
        modifier = modifier
            .aspectRatio(2f)
            .clip(CircleShape)
            .clickable{
                if (swipeableState.currentValue == 0)
                    scope.launch {
                        swipeableState.animateTo(
                            1,
                            anim = tween(150, easing = LinearEasing)
                        )
                    }
                else
                    scope.launch {
                        swipeableState.animateTo(
                            0,
                            anim = tween(150, easing = LinearEasing)
                        )

                    }
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
                        if (swipeableState.currentValue == 0)
                            scope.launch {
                                swipeableState.animateTo(
                                    1,
                                    anim = tween(150, easing = LinearEasing)
                                )
                            }
                        else
                            scope.launch {
                                swipeableState.animateTo(
                                    0,
                                    anim = tween(150, easing = LinearEasing)
                                )

                            }
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
                    IntOffset(
                        x = (swipeableState.offset.value).roundToInt(),
                        y = yOffset
                    )
                }
                .size(with(LocalDensity.current) { marbleSize.toDp() })
                .clip(CircleShape)
                .background(OnSecondary2)
                .clickable{
                    if (swipeableState.currentValue == 0)
                        scope.launch {
                            swipeableState.animateTo(
                                1,
                                anim = tween(150, easing = LinearEasing)
                            )
                        }
                    else
                        scope.launch {
                            swipeableState.animateTo(
                                0,
                                anim = tween(150, easing = LinearEasing)
                            )

                        }
                }
        )
    }
}

@Composable
@Preview
private fun Preview(){
    MySwitch(Modifier.fillMaxSize(), 1, {})
    MySwitch(Modifier.fillMaxWidth(), 0, {})
}