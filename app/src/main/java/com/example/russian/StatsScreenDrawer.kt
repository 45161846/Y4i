package com.example.russian

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.russian.MyEnumClasses.TaskTopic
import com.example.russian.toolPackage.WordToTaskMapper

class StatsScreenDrawer{

    @Composable
    fun DrawLoading(loadingAmount: Int){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.dark_background))
        ){
            Text(
                text = "loading($loadingAmount)...",
                color = colorResource(id = R.color.light_background),
                fontSize = 30.sp
            )
        }
    }

    @Composable
    fun DrawContentScreen(listOfWords: List<Word>){
        val narechia = List(listOfWords.size) {
            WordToTaskMapper().wordToNarechieTask(listOfWords[it])
        }
        

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.dark_background))
        ) {
            items(
                itemContent = {
                    CardOfStats(n = narechia[it], listOfWords[it].percentage)
                },
                count = narechia.size
            )
        }

    }

    @Composable
    private fun CardOfStats(n: MyTaskNarechia, winRate: Float){
        val ans = n.options[n.correctAnswer]
        Row(horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(8.dp, 3.dp, 8.dp, 3.dp)
                .background(
                    colorResource(id = R.color.dark_background_light),
                    RoundedCornerShape(5.dp)
                )
        ){
            Text(
                text = ans!!,
                fontSize = 20.sp,
                color = colorResource(id = R.color.light_background),
                modifier = Modifier
                    .padding(8.dp, 3.dp, 8.dp, 3.dp)
            )
            Row(horizontalArrangement = Arrangement.Absolute.Right,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(8.dp, 3.dp, 8.dp, 3.dp)
                    .background(
                        colorResource(id = R.color.dark_background_light),
                        RoundedCornerShape(5.dp)
                    )
            ){
                Spacer(
                    modifier = Modifier
                        .background(calculateColor(winRate), RoundedCornerShape(100))
                        .size(70.dp, 5.dp)
                        .padding(10.dp)
                )
                Spacer(modifier = Modifier
                    .background(Color.Transparent)
                    .size(10.dp, 2.dp)
                    .padding(10.dp))
            }
        }
    }

    private fun calculateColor(winRate: Float):Color{
        return Color((1F - winRate) * 2, winRate * 2, 0F, alpha = 1F)
    }

    @Composable
    @Preview
    private fun Preview(){
        DrawContentScreen(listOfWords = listOf(
            Word("в*век;;___ не забуду", TaskTopic().NARECHI9,1F),
            Word("на голову;;упал снег с ветки|выше", TaskTopic().NARECHI9,0.9F),
            Word("на*право", TaskTopic().NARECHI9,0.2F),
            Word("подобру-поздорову", TaskTopic().NARECHI9,0F),
            Word("на ура;;решение было принято", TaskTopic().NARECHI9,1F),
            Word("по*этому;;он так поступил", TaskTopic().NARECHI9,1F),
            Word("по*среди", TaskTopic().NARECHI9,0.5F),
        ))
    }

}