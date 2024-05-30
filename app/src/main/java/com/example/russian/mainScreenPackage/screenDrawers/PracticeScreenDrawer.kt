package com.example.russian.mainScreenPackage.screenDrawers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.russian.MyEnumClasses.TaskTopic
import com.example.russian.R

@Composable
fun DrawPracticeContent(
    paddingValues: PaddingValues,
    startGame: (topic: Int) -> Unit
) {

    val backColor = colorResource(id = R.color.dark_background)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backColor)
            .padding(paddingValues)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        OpenGameButtonButton(
            description = "Наречия",
            taskTopic = TaskTopic().NARECHI9,
            startGame = startGame
        )
        OpenGameButtonButton(
            description = "Паронимы",
            taskTopic = TaskTopic().PARONIM,
            startGame = startGame
        )

        OpenGameButtonButton(
            description = "Ударения",
            taskTopic = TaskTopic().YDARENI9,
            startGame = startGame
        )
    }

}

@Composable
private fun OpenGameButtonButton(
    taskTopic: Int,
    description: String,
    startGame: (topic: Int) -> Unit
){

    Button(modifier = Modifier
        .padding(20.dp, 25.dp)
        .size(400.dp, 100.dp),

        onClick = {
            startGame(taskTopic)
        }

    ) {
        Text(text = description,
            fontSize = 30.sp)
    }
}