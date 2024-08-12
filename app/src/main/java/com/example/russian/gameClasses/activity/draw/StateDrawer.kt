package com.example.russian.gameClasses.activity.draw

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.russian.architecture.data.entity.Spelling
import com.example.russian.architecture.data.entity.Word
import com.example.russian.architecture.data.entity.WordWithSpellings
import com.example.russian.gameClasses.activity.draw.hood.HoodDrawer
import com.example.russian.gameClasses.activity.draw.regular.ButtonDrawer
import com.example.russian.gameClasses.viewmodel.hood.HoodState
import com.example.russian.tasks.TaskStateMapper
import com.example.russian.toolPackage.WordToTaskMapper
import com.example.russian.ui.theme.PrimaryBackground
import com.example.russian.ui.theme.Typography


class StateDrawer {

    companion object{

        @Composable
        fun Screen(
            taskState: TaskUIState
        ) {
            when(taskState){

                is TaskUIState.Loading -> Loading()

                is TaskUIState.TaskUI -> {

                    Column {
                        Hood(taskState.hoodState)
                        Buttons(buttonStates = taskState.buttonStates)
                    }
                }
                is TaskUIState.YdareniaTaskUI ->{
                    Hood(hoodState = taskState.hoodState)
                    Letters(letterStates =  taskState.letterStates)
                }
            }
        }

        @Composable
        private fun Loading(){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(PrimaryBackground)
            ){
                LoadingContent()
            }

        }

        @Composable
        private fun Hood(hoodState: HoodUIState){
            HoodDrawer.Hood(state = hoodState)
        }

        @Composable
        private fun Buttons(buttonStates: List<ButtonUIState>){
            ButtonDrawer.Buttons(states = buttonStates)
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

    val wordWitSpelling = WordWithSpellings(
        testWord,
        spellings
    )

    val task = WordToTaskMapper.wordWithSpellingToTask(wordWitSpelling)

    val taskState1 = TaskStateMapper.taskToState(task, HoodState(),{}, {})

    val taskState2 = TaskUIState.Loading

    StateDrawer.Screen(taskState1)
}
