package com.example.russian.main.ui.draw.game

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.russian.main.enums.Letters
import com.example.russian.main.tasks.ydarenia.SingleLetter
import com.example.russian.main.ui.state.AnswerColor
import com.example.russian.main.ui.state.LetterUIState
import com.example.russian.main.ui.theme.LightGreen
import com.example.russian.main.ui.theme.RussianTheme
import com.example.russian.main.ui.theme.answerColor

class GameActivityYdareniaDrawer {

    companion object {

        @OptIn(ExperimentalLayoutApi::class)
        @Composable
        fun Ydareni9Content(
            states: List<LetterUIState>, modifier: Modifier
        ) {
//            val backSoglColor = MaterialTheme.colorScheme.primaryContainer
            val glasTextColor = MaterialTheme.colorScheme.onPrimaryContainer


            FlowRow(
                verticalArrangement = Arrangement.Center,
                horizontalArrangement = Arrangement.Center,
                modifier = modifier
                    .background(MaterialTheme.colorScheme.surfaceVariant),
            ) {
                states.forEach {
                    when (it) {
                        is LetterUIState.Sogl -> DrawSogl(
                            it.letter,
                            Modifier
                                .align(Alignment.CenterVertically)
                        )

                        is LetterUIState.Glas -> {
                            val borderColor =
                                if (it.borderColor == AnswerColor.UNSPECIFIED) glasTextColor
                                else MaterialTheme.answerColor(it.borderColor)
                            DrawGlas(
                                s = it.letter,
                                borderColor,
                                it.onClick
                            )
                        }
                    }
                }
            }
        }

        @Composable
        fun DrawSogl(s: SingleLetter, modifier: Modifier) {
            val letterColor = MaterialTheme.colorScheme.onSurfaceVariant
            Text(
                modifier = modifier,
                color = letterColor,
                text = s.letter,
                fontSize = 42.sp,
//                textAlign = TextAlign.Center,
            )

        }

        @Composable
        fun DrawGlas(s: SingleLetter, color: Color, onAnswer: () -> Unit) {

//            val interactionSource = remember {
//                MutableInteractionSource()
//            }
            Box(
                modifier = Modifier
                    .padding(3.dp)
                    .size(50.dp, 80.dp)
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        RoundedCornerShape(25)
                    )
                    .border(3.5.dp, color, RoundedCornerShape(25))
                    .clickable(interactionSource = null, indication = null, onClick =
                    {
                        onAnswer()
                    })
            ) {

                Text(
                    color = color,
                    text = s.letter.lowercase(),
                    fontSize = 40.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentHeight(align = Alignment.CenterVertically),
                )

            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun YdarPreview() {

    val states = listOf(
        LetterUIState.Sogl(
            SingleLetter("д", Letters.SOGLASNA9),
        ),
        LetterUIState.Glas(
            0, SingleLetter("о", Letters.YDARNA9),

            ) {},
        LetterUIState.Sogl(
            SingleLetter("м", Letters.SOGLASNA9),

            ),
        LetterUIState.Sogl(
            SingleLetter("д", Letters.SOGLASNA9),

            ),
        LetterUIState.Glas(
            0, SingleLetter("о", Letters.YDARNA9),

            ) {},
        LetterUIState.Sogl(
            SingleLetter("м", Letters.SOGLASNA9),

            ), LetterUIState.Sogl(
            SingleLetter("д", Letters.SOGLASNA9),

            ),
        LetterUIState.Glas(
            0, SingleLetter("о", Letters.YDARNA9),

            ) {},
        LetterUIState.Sogl(
            SingleLetter("м", Letters.SOGLASNA9),

            )
    )

    RussianTheme {
        GameActivityYdareniaDrawer.Ydareni9Content(
            states, Modifier
        )
    }
}
