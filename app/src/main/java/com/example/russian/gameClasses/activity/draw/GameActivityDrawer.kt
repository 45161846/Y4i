package com.example.russian.gameClasses.activity.draw

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.russian.MyEnumClasses.ButtonMode
import com.example.russian.architecture.data.entity.Spelling
import com.example.russian.architecture.data.entity.Word
import com.example.russian.architecture.data.entity.WordWithSpellings
import com.example.russian.gameClasses.viewmodel.hood.HoodState
import com.example.russian.gameClasses.viewmodel.hood.HoodStateInterface
import com.example.russian.tasks.TaskInterface
import com.example.russian.tasks.ydarenia.Ydareni9Task
import com.example.russian.toolPackage.WordToTaskMapper
import com.example.russian.ui.theme.GameButtonFirstColor
import com.example.russian.ui.theme.GameButtonSecondColor
import com.example.russian.ui.theme.GameButtonTextStyle
import com.example.russian.ui.theme.GameButtonThirdColor
import com.example.russian.ui.theme.LightGreen
import com.example.russian.ui.theme.LightRed
import com.example.russian.ui.theme.OnSecondary1
import com.example.russian.ui.theme.PrimaryBackground
import com.example.russian.ui.theme.Typography

class GameActivityDrawer {

    companion object : DrawerInterface {

        @Composable
        override fun Screen(
            task: TaskInterface?,
            hood: HoodStateInterface,
            onAnswered: (Boolean) -> Unit
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(PrimaryBackground)
            ) {

                val spacerModifier = Modifier.weight(1F)

                task?.let {
                    Hood(hood)

                    TaskContent(
                        task = it,
                        spacerModifier,
                        onAnswered = onAnswered
                    )
                } ?: // if task has not been assigned yet loading screen
                TaskContent(task = null, spacerModifier, onAnswered)
            }
        }

        @Composable
        private fun TaskContent(
            task: TaskInterface?,
            spacerModifier: Modifier,
            onAnswered: (Boolean) -> Unit
        ) {
            task?.let {

                //I do special UI for ydareni9 task
                if(task is Ydareni9Task){
                    GameActivityYdareniaDrawer.Ydareni9Content(task = task, onAnswered)
                    return
                }

                NotNullTaskContent(task, spacerModifier, onAnswered)

            } ?: NullTaskContent()
        }

        @Composable
        private fun Hood(hood: HoodStateInterface) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 56.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Counter(
                    text = "Ошибок: ${hood.incorrectCounter()}",
                    backColor = LightRed
                )

                Counter(
                    text = "Правильно: ${hood.correctCounter()}",
                    backColor = LightGreen
                )
            }
        }

        @Composable
        private fun Counter(text: String, backColor: Color) {
            RoundTextField(text = text, backColor = backColor)
        }

        @Composable
        private fun RoundTextField(
            text: String,
            backColor: Color,
            style: TextStyle = Typography.bodyLarge
        ) {
            Text(
                text = text,
                modifier = Modifier
                    .background(
                        color = backColor,
                        RoundedCornerShape(10.dp)
                    )
                    .padding(12.dp),
                style = style
            )
        }


        @Composable
        private fun NullTaskContent() {
            Text(
                text = "Загрузка...",
                modifier = Modifier
                    .fillMaxSize()

            )
        }

        @Composable
        private fun NotNullTaskContent(
            task: TaskInterface,
            spacerModifier: Modifier,
            onAnswered: (Boolean) -> Unit
        ) {
            ContextWord(text = task.getTaskText())

            Spacer(modifier = spacerModifier)

            task.getPosibleVariants().forEachIndexed { index, str ->
                OptionButton(
                    textValue = str,
                    isCorrect = task.isCorrect(index),
                    backColor = buttonColor(index),
                    onClick = onAnswered,
                    //reset = true
                )
            }
            Spacer(modifier = spacerModifier)
        }

        private fun buttonColor(index: Int) = mapOf(
            0 to GameButtonFirstColor,
            1 to GameButtonSecondColor,
            2 to GameButtonThirdColor,
            3 to Color.White,
            4 to Color.Blue
        )[index] ?: throw RuntimeException("Too many options, not enough colors")

        @Composable
        private fun ContextWord(text: String) {
            if (text.isNotEmpty()) {
                RoundTextField(text = text, backColor = OnSecondary1, style = Typography.titleLarge)
            }
        }

        @Composable
        private fun OptionButton(
            textValue: String,
            isCorrect: Boolean,
            backColor: Color,
            onClick: (Boolean) -> Unit,
            reset: Boolean = false
        ) {

            var buttonState by remember {
                mutableStateOf(ButtonMode.TASK)
            }

            if (reset){
                buttonState = ButtonMode.TASK
            }

            val shape = RoundedCornerShape(8)

            Button(
                onClick = {
                    buttonState =
                        if (isCorrect) ButtonMode.ANSWER_CORRECT else ButtonMode.ANSWER_WRONG
                    onClick(isCorrect)
                },
                modifier = Modifier
                    .padding(vertical = 18.dp)
                    .border(3.dp, backColor(buttonState, Color.Black), shape),
                shape = shape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = backColor
                )
            ) {
                Text(
                    text = textValue,
                    style = GameButtonTextStyle,
                    modifier = Modifier
                        .padding(horizontal = 48.dp, vertical = 16.dp)
                )
                //buttonState = ButtonMode.TASK
            }
        }

        private fun backColor(buttonState: ButtonMode, defaultColor: Color) =
            when(buttonState){
                ButtonMode.TASK -> defaultColor
                ButtonMode.ANSWER_CORRECT -> Color.Green
                ButtonMode.ANSWER_WRONG -> Color.Red
            }
    }
}

@Preview
@Composable
private fun Preview() {

    val testWord = Word(
        value = "аэропОрт",
        topic = 2,
        percentage = -1F
    )

    val spellings = listOf<Spelling>(

    )

    val wordWitSpelling = WordWithSpellings(
        testWord,
        spellings
    )

    val testTask = WordToTaskMapper.wordWithSpellingToTask(wordWitSpelling)

    val testHood = HoodState()

    GameActivityDrawer.Screen(task = testTask, hood = testHood) {}
}