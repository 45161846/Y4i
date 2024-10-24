package com.example.russian.ui.draw.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.russian.ui.draw.test.testClickableWord
import com.example.russian.ui.state.ButtonUIState
import com.example.russian.ui.state.ContextTextState
import com.example.russian.ui.state.HoodUIState
import com.example.russian.ui.state.TaskUIState
import com.example.russian.ui.state.hood.GameNavigationState
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
            val navigationState = taskState.navigationState

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
                            val hoodWeight = 1F
                            Hood(
                                hoodState = taskState.hoodState,
                                modifier = Modifier
                                    .weight(hoodWeight)
                                    .fillMaxWidth()
                                    .padding(horizontal = 18.dp),
                                spacerModifier = Modifier
                                    .weight(hoodWeight / 3)
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

                            HoodDrawer.GameNavigationBottom(
                                navigationState
                            )
                        }

                        is TaskUIState.YdareniaTaskUI -> {
                            val hoodWeight = 1F
                            Hood(
                                hoodState = taskState.hoodState,
                                modifier = Modifier
                                    .weight(hoodWeight)
                                    .fillMaxWidth()
                                    .padding(horizontal = 18.dp),
                                spacerModifier = Modifier
                                    .weight(hoodWeight / 3)
                            )
                            Ydarenia(
                                taskState,
                                Modifier
                                    .fillMaxWidth()
                                    .weight(5F)
                            )
                            HoodDrawer.GameNavigationBottom(
                                navigationState
                            )
                        }

                        is TaskUIState.ClickableText -> {
                            val hoodWeight = 1F
                            Hood(
                                hoodState = taskState.hoodState,
                                modifier = Modifier
                                    .weight(hoodWeight)
                                    .fillMaxWidth()
                                    .padding(horizontal = 18.dp),
                                spacerModifier = Modifier
                                    .weight(hoodWeight / 3)
                            )
                            ClickableTextTaskContent(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(5F)
                                    .padding(horizontal = 8.dp),
                                words = taskState.words,
                                onWordClick = { ind ->
                                    taskState.onWordClick(ind)
                                },
                                onAnswered = navigationState.onAnswerClick
                            )
                            HoodDrawer.GameNavigationBottom(
                                navigationState
                            )
                        }

                        else -> {
                            TODO()
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
            modifier: Modifier,
            spacerModifier: Modifier
        ) {
            HoodDrawer.Hood(state = hoodState, modifier, spacerModifier)
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
@Preview(
    showSystemUi = true,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
private fun Preview() {

    val taskState2 = TaskUIState.ClickableText(
        testClickableWord(),
        HoodUIState.NoTimer(0, 0),
        GameNavigationState(
            {}, {}, {}, {}, true, true, false
        ),
        {}
    )

    StateDrawer.Screen(taskState2)
}
