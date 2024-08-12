package com.example.russian.gameClasses

import android.util.Log
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.russian.MyEnumClasses.ButtonMode
import com.example.russian.tasks.narecia.MyTaskNarechia
import com.example.russian.R
import com.example.russian.ui.theme.RussianTheme
import kotlin.math.max

class GameActivityDrawerClass(val viewmodel: MyGameViewModelImpl?) {


    @Composable
    fun Greeting(
        task: MyTaskNarechia,
        r: Int,
        w: Int,
        typeOfVariant: ButtonMode,
        onBackPressedDispatcher: OnBackPressedDispatcher
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.dark_background))
        ) {
            RightWrongRow(r = r, w = w)

            if (task.contextText.isNotEmpty()) {
                ContextWord(task.contextText)
            }

            MyTonalButton(
                col = colorResource(id = R.color.first_answer),
                text = task.options[0]!!,
                isCorrect = task.correctAnswerIndex == 0,
                buttonMode = typeOfVariant,

                )

            MyTonalButton(
                col = colorResource(id = R.color.second_answer),
                text = task.options[1]!!,
                isCorrect = task.correctAnswerIndex == 1,
                buttonMode = typeOfVariant,

                )
            if (task.options.size > 2) {
                MyTonalButton(
                    col = colorResource(R.color.third_answer),
                    text = task.options[2]!!,
                    isCorrect = task.correctAnswerIndex == 2,
                    buttonMode = typeOfVariant,

                    )
            }

        }
        BackButton(onBackPressedDispatcher)
    }

    @Composable
    fun BackButton(onBackPressedDispatcher: OnBackPressedDispatcher) {
        IconButton(

            onClick = {
                onBackPressedDispatcher.onBackPressed()
            },
            modifier = Modifier
                .size(80.dp)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.back_comback_hom_svgrepo_com),
                contentDescription = "back icon",
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }


    @Composable
    fun RightWrongRow(r: Int, w: Int) {

        val textRight = "Правильно: $r"
        val textWrong = "Ошибок: $w"

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(20.dp, 15.dp, 20.dp, 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = textRight,
                modifier = Modifier
                    .size((12.5 * textRight.length).dp, 50.dp)
                    .background(
                        colorResource(id = R.color.answer_correct_field_back),
                        RoundedCornerShape(15.dp)
                    )
                    .border(
                        2.dp,

                        colorResource(id = R.color.border_answer),
                        RoundedCornerShape(15.dp)
                    )
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .wrapContentHeight(Alignment.CenterVertically),
                fontSize = 20.sp,
            )

            Text(
                text = textWrong,
                modifier = Modifier
                    .size((12.5 * textWrong.length).dp, 50.dp)
                    .background(
                        colorResource(id = R.color.answer_wrong_field_back),
                        RoundedCornerShape(15.dp)
                    )
                    .border(
                        2.dp,
                        colorResource(id = R.color.border_answer),
                        RoundedCornerShape(15.dp)
                    )
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .wrapContentHeight(Alignment.CenterVertically),
                fontSize = 20.sp
            )
        }
    }

    @Composable
    fun ContextWord(word: String) {
        val textSize = 680 / max(17, word.length)
        Text(
            text = word,
            fontSize = textSize.sp,
            modifier = Modifier
                .background(
                    colorResource(id = R.color.light_background),
                    RoundedCornerShape(15.dp)
                )
                .padding(horizontal = 10.dp, vertical = 3.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .wrapContentHeight(Alignment.CenterVertically)
        )
    }

    @Composable
    fun MyTonalButton(
        col: Color,
        text: String,
        isCorrect: Boolean,
        buttonMode: ButtonMode = ButtonMode.TASK,
    ) {

        val borderColorID = getBorderColor(isCorrect, buttonMode)

        FilledTonalButton(
            onClick = {
                if (isCorrect) {
                    viewmodel!!.correctAnswer()
                    Log.d("myTag_buttons", "correct")
                } else {
                    viewmodel!!.incorrectAnswer()
                    Log.d("myTag_buttons", "incorrect")
                }
            },
            modifier = Modifier
                .padding(20.dp, 25.dp)
                .size(400.dp, 100.dp),

            border = BorderStroke(
                if (buttonMode == ButtonMode.TASK) {
                    2.dp
                } else if (isCorrect) {
                    5.dp
                } else {
                    2.dp
                },

                colorResource(id = borderColorID)
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = col,
                contentColor = colorResource(
                    id =
                    borderColorID
                )
            )
        ) {
            Text(
                text = text,
                fontSize = (450 / max(12, text.length)).sp
            )
        }
    }

    private fun getBorderColor(isCorrect: Boolean, typeOfVariant: ButtonMode): Int {

        return when (typeOfVariant) {
            ButtonMode.TASK -> R.color.border_answer
            ButtonMode.ANSWER_CORRECT -> {
                when (isCorrect) {
                    true -> R.color.border_answer_correct
                    false -> R.color.border_answer
                }
            }

            ButtonMode.ANSWER_WRONG -> {
                when (isCorrect) {
                    true -> R.color.border_answer_wrong
                    false -> R.color.border_answer
                }
            }
        }

    }


    @Composable
    fun GreetingPreview() {
        val testTask = "на*зад;;повернуть"
        RussianTheme {
            Greeting(
                MyTaskNarechia(0, testTask),
                0,
                0,
                ButtonMode.ANSWER_CORRECT,

                OnBackPressedDispatcher()
            )
        }
    }
}