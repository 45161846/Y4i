package com.example.russian.main.prac.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.russian.main.settings.StatDisplaySetting

@Composable
fun ShimmerStats(
    displaySetting: StatDisplaySetting,
    lineHeight: Dp,
    color: Color,
    shape: Shape,
    modifier: Modifier = Modifier
){

    Row(
        modifier = modifier
        , verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 10.dp)
                .height(lineHeight)
                .width(100.dp)
                .background(color, shape)
        )

        Spacer(
            modifier = Modifier
                .weight(1F)
        )

        if (displaySetting.showTypeIcon) {
            Spacer(
                modifier = Modifier
                    .padding(4.dp)
                    .size(32.dp)
                    .background(color, shape)
            )
        }


        if (displaySetting.showWinrate) {

            Spacer(
                modifier = Modifier
                    .padding(10.dp, 0.dp)
                    .size(width = 2.3 * lineHeight, height = lineHeight)
                    .background(color, shape)
            )

        }


        if (displaySetting.showIndicator) {
            Spacer(
                modifier = Modifier
                    .size(70.dp, 5.dp)
                    .background(color, shape)
            )
            Spacer(
                modifier = Modifier
                    .background(Color.Transparent)
                    .size(10.dp, 2.dp)
            )
        }
    }

}