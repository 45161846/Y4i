package com.example.russian.ui.draw.practice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.russian.enums.TaskTopic
import com.example.russian.R
import com.example.russian.ui.theme.FiltersScreenButtonActive
import com.example.russian.ui.theme.family

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
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = FiltersScreenButtonActive
        ),
        shape = RoundedCornerShape(15)

    ) {
        Text(
            text = description,
            fontSize = 30.sp,
            fontFamily = family
        )
    }
}

@Preview
@Composable
private fun PracticePreview(){
    DrawPracticeContent(paddingValues = PaddingValues()) {
        
    }
}