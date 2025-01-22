package com.example.russian.game.ui.draw.game

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.russian.R
import com.example.russian.game.ui.draw.game.HoodDrawer.Companion.GameNavigationBottom
import com.example.russian.game.ui.state.HoodUIState
import com.example.russian.game.ui.state.hood.GameNavigationState
import com.example.russian.main.theme.LightGreen
import com.example.russian.main.theme.LightRed


class HoodDrawer {

    companion object {
        @Composable
        fun Hood(
            state: HoodUIState, modifier: Modifier, spacerModifier: Modifier
        ) {

            Spacer(spacerModifier.background(Color.Transparent))

            when (state) {
                is HoodUIState.NoTimer -> {
                    HoodNoTimer(
                        state, modifier
                    )
                }
            }
        }

        @Composable
        private fun HoodNoTimer(
            state: HoodUIState.NoTimer, modifier: Modifier
        ) {
            Row(
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                verticalAlignment = Alignment.Top,
                modifier = modifier
            ) {
                Counter(text = "Правильно: ${state.correct}", backColor = LightGreen)
                Counter(text = "Ошибок: ${state.incorrect}", backColor = LightRed)
            }


        }

        @Composable
        fun GameNavigationBottom(
            state: GameNavigationState
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .safeGesturesPadding(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                val buttonModifier = Modifier
                    .weight(3F)
                    .padding(horizontal = 6.dp)

                val defaultColor = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )

                if (state.showPrev) {
                    Button(
                        modifier = buttonModifier,
                        onClick = state.onPreviousClick,
                        colors = defaultColor,
                    ) {
                        Text(
                            "Previous task", color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                if (state.showAnswerButton) {
                    Button(
                        modifier = buttonModifier,
                        onClick = state.onAnswerClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            "Answer", color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

                if (state.showNext) {

                    Row(
                        modifier = buttonModifier.padding(0.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = state.onNextClick,
                            modifier = Modifier.weight(1F),
                            colors = defaultColor
                        ) {
                            Text(
                                "Next task", color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }

                        Icon(
                            modifier = Modifier
                                .size(36.dp)
                                .padding(end = 4.dp)
                                .clickable {
                                    state.toTaskClick()
                                },
                            imageVector = ImageVector.vectorResource(R.drawable.double_arrow_right),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun Preview() {

    Row(
        modifier = Modifier
            .wrapContentSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        GameNavigationBottom(
            GameNavigationState({}, {}, {}, {}, true, true, false
            ),

            )
    }


}