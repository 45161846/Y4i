package com.example.russian.main.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.russian.main.theme.TransparentBlack
import com.example.russian.main.theme.TransparentWhite
import com.example.russian.main.theme.isLight

@Composable
fun contrastPortionedColor(winRate: Double): Color {
    if (winRate < 0) {
        return TransparentBlack
    }

    val start = if(MaterialTheme.colorScheme.isLight()) TransparentBlack else TransparentWhite
    val end = MaterialTheme.colorScheme.primary.invert().maximizeBrightness()

    return Color(
        red = portion(start.red, end.red, winRate),
        green = portion(start.green, end.green, winRate),
        blue = portion(start.blue, end.blue, winRate),
        alpha = portion(start.alpha, end.alpha, 0.33 + winRate * 0.67)
    )
}

private fun portion(start: Float, end: Float, winrate: Double): Float{
    return start + (end - start) * winrate.toFloat()
}

private fun Color.maximizeBrightness(): Color {

    val maxFil = maxOf(this.red, this.green, this.blue)

    return Color(
        red = this.red / maxFil,
        green = this.green / maxFil,
        blue = this.blue / maxFil,
        alpha = 1F
    )
}
private fun Color.invert(): Color {
    val inverted = Color(
        red = 1F - this.red,
        green = 1F - this.green,
        blue = 1F - this.blue,
        alpha = 1F
    )
    val maxFil = maxOf(inverted.red, inverted.green, inverted.blue)

    return Color(
        red = inverted.red / maxFil,
        green = inverted.green / maxFil,
        blue = inverted.blue / maxFil,
        alpha = 1F
    )
}