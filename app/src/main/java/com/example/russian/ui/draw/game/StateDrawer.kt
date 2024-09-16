package com.example.russian.ui.draw.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.russian.ui.state.ButtonUIState
import com.example.russian.ui.state.ContextTextState
import com.example.russian.ui.state.HoodUIState
import com.example.russian.ui.state.TaskUIState
import com.example.russian.ui.text.contextWordStyle
import com.example.russian.ui.theme.PrimaryBackground
import com.example.russian.ui.theme.Typography


val backgroundColor = PrimaryBackground

class StateDrawer {

    companion object {

        @Composable
        fun Screen(
            taskState: TaskUIState
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor)
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (taskState) {

                        is TaskUIState.Loading -> Loading()

                        is TaskUIState.TaskUI -> {
                            Hood(
                                taskState.hoodState,
                                modifier = Modifier
                                    .weight(1F)
                                    .fillMaxWidth()
                            )

                            if (taskState.contextState is ContextTextState.Context) {
                                ContextText(
                                    state = taskState.contextState,
                                    modifier = Modifier.weight(1F)
                                )
                            } else {
                                ContextText(
                                    state = ContextTextState.Context(
                                        "",
                                        Color.Black,
                                        Color.Black
                                    ), modifier = Modifier.weight(1F)
                                )
                            }

                            Buttons(
                                buttonStates = taskState.buttonStates,
                                modifier = Modifier.wrapContentHeight()
                            )

                        }

                        is TaskUIState.YdareniaTaskUI -> {
                            Hood(
                                hoodState = taskState.hoodState,
                                modifier = Modifier
                                    .weight(1F)
                                    .fillMaxWidth()
                            )
                            Ydarenia(taskState, Modifier.fillMaxWidth().weight(5F))

                        }
                    }
                }


            }


        }

        @Composable
        private fun ContextText(
            state: ContextTextState.Context,
            modifier: Modifier
        ) {
            Text(
                modifier = modifier
                    .wrapContentSize(Alignment.Center),
                text = state.text,
                textAlign = TextAlign.Center,
                style = contextWordStyle,
                color = state.textColor
            )
        }

        @Composable
        private fun Loading() {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                LoadingContent()
            }

        }

        @Composable
        private fun Hood(
            hoodState: HoodUIState,
            modifier: Modifier
        ) {
            HoodDrawer.Hood(state = hoodState, modifier)
        }

        @Composable
        private fun Buttons(
            buttonStates: List<ButtonUIState>,
            modifier: Modifier
        ) {
            ButtonDrawer.Buttons(
                states = buttonStates,
                modifier = modifier
            )
        }


        @Composable
        private fun LoadingContent() {
            Text(
                style = Typography.titleLarge,
                text = "Загрузка...",
            )
        }

        @Composable
        private fun Ydarenia(state: TaskUIState.YdareniaTaskUI, modifier: Modifier) {
            GameActivityYdareniaDrawer.Ydareni9Content(
                state.letterStates, modifier
            )
        }
    }
}

@Composable
@Preview
private fun Preview() {

    val taskState2 = TaskUIState.Loading

    StateDrawer.Screen(taskState2)
}
