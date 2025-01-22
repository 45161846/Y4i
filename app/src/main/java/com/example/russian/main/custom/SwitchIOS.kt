package com.example.russian.main.custom



import android.annotation.SuppressLint
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun CustomSwitch(
    initialChecked: Boolean,
    height: Dp,
    width: Dp = height * 2F,
    paddingRatio: Float = 0.3F,
    shape: Shape = RoundedCornerShape(30),
    colors: SwitchColors = SwitchColors.default(),
    animationTime: Int = 100,
    onCheckChange: (Boolean) -> Unit
) {

    val marbleRadius = height / (2 * (1 + paddingRatio))
    val borderWidth = marbleRadius * paddingRatio / 2.5F
    val startXOffset = marbleRadius * paddingRatio
    val endXOffset = width - marbleRadius * (2 + paddingRatio)

    val coroutineScope = rememberCoroutineScope()

    var targetRatio by remember(initialChecked) {
        mutableFloatStateOf(if(initialChecked) 1F else 0F)
    }

    val ratio = remember {
        Animatable(if(initialChecked) 1F else 0F)
    }

    val color = remember(initialChecked) {
        Animatable(
            if(initialChecked) colors.backgroundOn else colors.backgroundOff
        )
    }

    val onClick = {
        targetRatio = (targetRatio + 1) % 2

        coroutineScope.launch {

            ratio.animateTo(
                targetRatio,
                tween(animationTime)
            )

            color.animateTo(
                if(targetRatio == 1F) colors.backgroundOn else colors.backgroundOff,
                tween(animationTime * 2)
            )
        }
        onCheckChange(targetRatio == 1F)
    }

    Box(
        modifier = Modifier
            .size(width, height)
            .clip(shape)
            .clickable {
                onClick()
            }
            .background(
                color.value
            )

    ) {

        val xOffset by remember(ratio.value) {
            mutableStateOf(startXOffset + (endXOffset - startXOffset) * ratio.value)
        }

        Box(
            modifier = Modifier
                .size(marbleRadius * 2)
                .offset(
                    xOffset,
                    marbleRadius * paddingRatio
                )
                .aspectRatio(1F)
                .clip(shape)
                .clickable { onClick() }
                .background(
                    if (targetRatio == 1F) colors.backgroundOff
                    else colors.backgroundOn
                    )
        )
    }
}

data class SwitchColors(
    val backgroundOff: Color,
    val backgroundOn: Color,
    val marble: Color,
    ) {
    companion object {
        @Composable
        fun default() = SwitchColors(
            backgroundOff = MaterialTheme.colorScheme.primaryContainer,
            backgroundOn = MaterialTheme.colorScheme.primary,
            marble = Color.White
        )
    }
}


@Preview
@Composable
private fun PreviewOff() {
    CustomSwitch(
        false,
        100.dp
    ){}
}

@Preview
@Composable
private fun PreviewOn() {
    CustomSwitch(
        true,
        100.dp
    ){}
}