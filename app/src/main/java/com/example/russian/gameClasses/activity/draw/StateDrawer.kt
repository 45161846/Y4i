package com.example.russian.gameClasses.activity.draw

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
import com.example.russian.architecture.data.olddata.entity.Spelling
import com.example.russian.architecture.data.olddata.entity.Word
import com.example.russian.architecture.data.olddata.entity.WordWithSpellings
import com.example.russian.architecture2.ui.state.ButtonUIState
import com.example.russian.architecture2.ui.state.ContextTextState
import com.example.russian.architecture2.ui.state.HoodUIState
import com.example.russian.architecture2.ui.state.LetterUIState
import com.example.russian.architecture2.ui.state.TaskUIState
import com.example.russian.architecture2.ui.text.contextWordStyle
import com.example.russian.gameClasses.activity.draw.hood.HoodDrawer
import com.example.russian.gameClasses.activity.draw.regular.ButtonDrawer
import com.example.russian.gameClasses.viewmodel.hood.HoodState
import com.example.russian.tasks.TaskStateMapper
import com.example.russian.toolPackage.WordToTaskMapper
import com.example.russian.ui.theme.PrimaryBackground
import com.example.russian.ui.theme.Typography


val backgroundColor = PrimaryBackground

class StateDrawer {

    companion object{

        @Composable
        fun Screen(
            taskState: TaskUIState
        ) {

            Box(modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
            ){
                when(taskState){

                    is TaskUIState.Loading -> Loading()

                    is TaskUIState.TaskUI -> {

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Hood(
                                taskState.hoodState,
                                modifier = Modifier
                                    .weight(1F)
                                    .fillMaxWidth()
                            )

                            if(taskState.contextState is ContextTextState.Context){
                                ContextText(
                                    state = taskState.contextState,
                                    modifier = Modifier.weight(1F)
                                )
                            }else{
                                ContextText(state = ContextTextState.Context("", Color.Black, Color.Black), modifier = Modifier.weight(1F))
                            }

                            Buttons(
                                buttonStates = taskState.buttonStates,
                                modifier = Modifier.wrapContentHeight()
                            )
                        }
                    }
                    is TaskUIState.YdareniaTaskUI ->{
                        Hood(
                            hoodState = taskState.hoodState,
                            modifier = Modifier
                        )
                        Letters(letterStates =  taskState.letterStates)
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
                    .wrapContentSize(Alignment.Center)

//                    .drawBehind {
//                        val strokeWidthPx = 3.dp.toPx()
//                        val verticalOffset = size.height - 2.sp.toPx()
//                        drawLine(
//                            color = state.lineColor,
//                            strokeWidth = strokeWidthPx,
//                            start = Offset(0f, verticalOffset),
//                            end = Offset(size.width, verticalOffset)
//                        )
//                    }
                ,
                text = state.text,
                textAlign = TextAlign.Center,
                style = contextWordStyle,
                color = state.textColor
            )
        }

        @Composable
        private fun Loading(){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
            ){
                LoadingContent()
            }

        }

        @Composable
        private fun Hood(
            hoodState: HoodUIState,
            modifier: Modifier
        ){
            HoodDrawer.Hood(state = hoodState, modifier)
        }

        @Composable
        private fun Buttons(
            buttonStates: List<ButtonUIState>,
            modifier: Modifier
        ){
            ButtonDrawer.Buttons(
                states = buttonStates,
                modifier = modifier
            )
        }

        @Composable
        private fun Letters(letterStates: List<LetterUIState>){

        }

        @Composable
        private fun LoadingContent(){
            Text(
                style = Typography.titleLarge,
                text = "Загрузка...",
            )
        }
    }

}

@Composable
@Preview
fun preview(){

    val testWord = Word(
        value = "сбоку|с боку|с-боку;ударить",
        topic = 0,
        percentage = -1F
    )

    val spellings = listOf<Spelling>(
        Spelling(0,0,"сбоку", true),
        Spelling(0,0,"с боку", true),
        Spelling(0,0,"с-боку", true),
    )

    val spellings2 = listOf<Spelling>(
        Spelling(0,0,"на четверенькахaaaaaaaaaaaaaaaaaaaaaaaaaa", true),
        Spelling(0,0,"на-четвереньках", true),
        Spelling(0,0,"начетвереньках", true),
    )

    val wordWitSpelling = WordWithSpellings(
        testWord,
        spellings2
    )

    val task = WordToTaskMapper.wordWithSpellingToTask(wordWitSpelling)

    val taskState1 = TaskStateMapper.taskToState(task, HoodState(),{}, {})

    val taskState2 = TaskUIState.Loading

    StateDrawer.Screen(taskState1)
}
