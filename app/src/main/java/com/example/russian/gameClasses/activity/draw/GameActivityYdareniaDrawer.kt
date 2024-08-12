package com.example.russian.gameClasses.activity.draw

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.russian.MyEnumClasses.ButtonMode
import com.example.russian.MyEnumClasses.Letters
import com.example.russian.R
import com.example.russian.tasks.TaskInterface
import com.example.russian.tasks.ydarenia.SingleLetter
import com.example.russian.tasks.ydarenia.Ydareni9Task
import kotlin.math.ceil
import kotlin.math.min

class GameActivityYdareniaDrawer {

    companion object{

        @Composable
        fun Ydareni9Content(
            task: TaskInterface,
            onAnswer: (Boolean) -> Unit
            ) {
            if(task is Ydareni9Task){
                DrawYdareni9Lettres(task = task, onAnswer)
            }else{
                Text(text = "Oops...")
            }
        }

        @Composable
        private fun DrawYdareni9Lettres(task: Ydareni9Task, onAnswer: (Boolean) -> Unit){
            val lettersInARow = 9
            val letters = task.letters
            val rowsSize = ceil(letters.size.toDouble() / lettersInARow).toInt()
            val rows = List(rowsSize){
                letters.slice(it * lettersInARow until min((it + 1) * lettersInARow, letters.size))
            }
            LazyColumn(
                userScrollEnabled = false,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .height(400.dp)
                    .fillMaxWidth()
            ) {
                items(
                    rowsSize,
                    itemContent = {
                        DrawLineOfLetters(l = rows[it], onAnswer)
                    }
                )
            }
        }

        @Composable
        fun DrawLineOfLetters(l: List<SingleLetter>, onAnswer: (Boolean) -> Unit){
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
                        DrawSingleLetter(s = l[it], onAnswer)
                    }
                )
            }
        }

        @Composable
        fun DrawSingleLetter(s: SingleLetter, onAnswer: (Boolean) -> Unit){
            if(s.type == Letters.SOGLASNA9){
                DrawSogl(s = s)
            }else{
                DrawGlas(s = s, onAnswer)
            }
        }

        @Composable
        fun DrawSogl(s: SingleLetter){
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
        fun DrawGlas(s: SingleLetter, onAnswer: (Boolean) -> Unit){

            val l = s.letter

            var mode by remember {
                mutableStateOf(ButtonMode.TASK)
            }

            val letterColor = when(mode){
                ButtonMode.TASK -> Color.Black
                ButtonMode.ANSWER_CORRECT -> Color.Green
                ButtonMode.ANSWER_WRONG -> Color.Red
            }

            Box(
                modifier = Modifier
                    .padding(3.dp)
                    .size(50.dp, 80.dp)
                    .background(
                        color = colorResource(id = R.color.ydar_back),
                        RoundedCornerShape(25)
                    )
                    .border(3.5.dp, letterColor, RoundedCornerShape(25))
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxSize(),
                    onClick = {
                        val isCorrect = s.type == Letters.YDARNA9

                        mode = if(isCorrect) ButtonMode.ANSWER_CORRECT else ButtonMode.ANSWER_WRONG

                        onAnswer(isCorrect)
                    }
                ) {
                    Text(
                        color = letterColor,
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