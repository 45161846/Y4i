package com.example.russian.main.custom

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.russian.R

@Composable
fun StarRating(
    rating: Float,
    modifier: Modifier = Modifier,
    starModifier: Modifier = Modifier,
    color: Color = Color.Unspecified
) {

    val starFill = List(5) {
        if (rating - it >= 1) StarFill.FILL
        else if (rating - it >= 0.5) StarFill.HALF
        else StarFill.EMPTY
    }

    Row(
        modifier = modifier
    ) {
        starFill.forEach {
            Icon(
                modifier = starModifier,
                painter = choseStar(it),
                contentDescription =  null,
                tint = color
            )
        }
    }
}

@Composable
private fun choseStar(filling: StarFill): Painter {
    return painterResource(
        when (filling) {
            StarFill.EMPTY -> R.drawable.star_empty
            StarFill.HALF -> R.drawable.star_half_filled
            StarFill.FILL -> R.drawable.star_filled
        }
    )
}

data class StarRatingColors(
    val activeStarColor: Color = Color.Unspecified,
    val emptyStarColor: Color = Color.Unspecified
)

private enum class StarFill {
    EMPTY, HALF, FILL
}


@Preview(showBackground = true)
@Composable
private fun RatingPreview(){
    StarRating(
        3.4F
    )
}