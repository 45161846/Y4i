package com.example.russian.gameClasses.activity.draw.regular

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.russian.architecture2.ui.state.ButtonUIState
import com.example.russian.ui.theme.GameButtonTextStyle

val buttonPadding = 18.dp

class ButtonDrawer {

    companion object {

        @Composable
        private fun MyButton(state: ButtonUIState, topPadding: Dp) {

            when (state) {
                is ButtonUIState.InProgress -> Progress(state, topPadding)
                is ButtonUIState.ShowAnswer -> Answer(state, topPadding)
            }

        }

        @Composable
        private fun Answer(state: ButtonUIState.ShowAnswer, topPadding: Dp) {
            DrawButton(
                text = state.text,
                backColor = state.backColor,
                borderColor = state.borderColor,
                topPadding,
                state.onClick
            )
        }


        @Composable
        private fun Progress(state: ButtonUIState.InProgress, topPadding: Dp) {
            DrawButton(
                text = state.text,
                backColor = state.backColor,
                borderColor = state.borderColor,
                topPadding,
                state.onClick
            )
        }

        @Composable
        private fun DrawButton(
            text: String,
            backColor: Color,
            borderColor: Color,
            topPadding: Dp,
            onClick: () -> Unit
        ) {

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = topPadding,
                        bottom = buttonPadding,
                        start = buttonPadding,
                        end = buttonPadding
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = backColor,
                ),
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(3.dp, borderColor),
                onClick = onClick
            ) {
                Text(
                    modifier = Modifier
                        .padding(24.dp),
                    style = GameButtonTextStyle,
                    text = text,
                    color = borderColor,
                    textAlign = TextAlign.Center
                )
            }

        }

        @Composable
        fun Buttons(
            states: List<ButtonUIState>,
            modifier: Modifier,
        ) {
            LazyColumn(
                userScrollEnabled = false,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
            ) {
                this.items(states.size) {
                    val topPadding = if (it == 0) 0.dp else buttonPadding
                    MyButton(states[it], topPadding)
                }
            }
        }
    }
}