package com.example.russian.ui.draw.game

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.russian.R
import com.example.russian.tasks.ydarenia.SingleLetter
import com.example.russian.ui.state.LetterUIState
import kotlin.math.ceil
import kotlin.math.min

class GameActivityYdareniaDrawer {

    companion object {

        @Composable
        fun Ydareni9Content(
            states: List<LetterUIState>, modifier: Modifier
        ) {

            val lettersInARow = 9
            val rowsSize = ceil(states.size.toDouble() / lettersInARow).toInt()
            val rows = List(rowsSize) {
                states.slice(it * lettersInARow until min((it + 1) * lettersInARow, states.size))
            }
            LazyColumn(
                userScrollEnabled = false,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
            ) {
                items(
                    rowsSize,
                    itemContent = {
                        DrawLineOfLetters(l = rows[it])
                    }
                )
            }
        }

        @Composable
        fun DrawLineOfLetters(l: List<LetterUIState>) {
            LazyRow(
                userScrollEnabled = false,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color.Transparent)
            ) {
                items(
                    l.size,
                    itemContent = {
                        val s = when (val letter = l[it]) {
                            is LetterUIState.Sogl -> letter.letter
                            is LetterUIState.Glas -> letter.letter
                        }

                        DrawSingleLetter(l[it])
                    }
                )
            }
        }

        @Composable
        fun DrawSingleLetter(state: LetterUIState) {
            when (state) {
                is LetterUIState.Sogl -> DrawSogl(s = state.letter)
                is LetterUIState.Glas -> DrawGlas(s = state.letter, state.color, state.onClick)
            }
        }

        @Composable
        fun DrawSogl(s: SingleLetter) {
            val letterColor = Color.White
            Text(
                color = letterColor,
                text = s.letter,
                fontSize = 42.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(align = Alignment.CenterVertically),
            )

        }

        @Composable
        fun DrawGlas(s: SingleLetter, color: Color, onAnswer: () -> Unit) {
            Box(
                modifier = Modifier
                    .padding(3.dp)
                    .size(50.dp, 80.dp)
                    .background(
                        color = colorResource(id = R.color.ydar_back),
                        RoundedCornerShape(25)
                    )
                    .border(3.5.dp, color, RoundedCornerShape(25))
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxSize(),
                    onClick = {
                        onAnswer()
                    }
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

}