package com.example.russian.main.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.russian.main.theme.AbsoluteWhite
import com.example.russian.main.theme.Black
import com.example.russian.main.theme.TransparentBlack
import com.example.russian.main.theme.TransparentWhite
import com.example.russian.main.theme.WhiteDD
import com.example.russian.main.theme.isLight

@Composable
fun contrastPortionedColor(
    worstColor: Color,
    bestColor: Color,
    winRate: Double
): Color {
    if (winRate < 0) {
        return (if(MaterialTheme.colorScheme.isLight()) Black else AbsoluteWhite).copy(
            alpha = 0.15f
        )
    }

    return Color(
        red = portion(worstColor.red, bestColor.red, winRate),
        green = portion(worstColor.green, bestColor.green, winRate),
        blue = portion(worstColor.blue, bestColor.blue, winRate),
        alpha = portion(worstColor.alpha, bestColor.alpha, 0.33 + winRate * 0.67)
    )
}

private fun portion(start: Float, end: Float, winrate: Double): Float{
    return start + (end - start) * winrate.toFloat()
}

fun Color.contrastText(): Color{

    val minFil = minOf(red, green, blue)
    return if(minFil < 0.33f) Color.White else Black

}

fun Color.multiply(times: Float) = Color(
    red = red * times,
    green = green * times,
    blue = blue * times
)

fun Color.maximizeBrightness(): Color {

    return this.copy(alpha = 1f)
}
fun Color.invert(): Color {
    return Color(
        red = alpha - this.red,
        green = alpha - this.green,
        blue = alpha - this.blue
    )
}

fun Color.invertWhite(): Color{
    val maxFil = maxOf(red, green, blue)

    val nextMin = 1 - maxFil

    val ratio = if (nextMin > 0){
        minOf(red, green, blue) / nextMin
    }else{
        Float.POSITIVE_INFINITY
    }

    return Color(
        red = red / ratio,
        green = green / ratio,
        blue = blue / ratio
    )

}