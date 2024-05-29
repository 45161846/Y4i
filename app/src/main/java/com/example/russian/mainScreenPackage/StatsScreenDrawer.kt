package com.example.russian.mainScreenPackage

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.russian.MyEnumClasses.TaskTopic
import com.example.russian.R
import com.example.russian.database.Word
import com.example.russian.toolPackage.WordToTaskMapper

class StatsScreenDrawer(val paddingValues: PaddingValues){

    @Composable
    fun DrawLoading(loadingAmount: Int){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = myModifier(paddingValues = paddingValues)
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
        LazyColumn(
            modifier = myModifier(paddingValues = paddingValues)
        ) {
            items(
                itemContent = {
                    CardOfStats(listOfWords[it], listOfWords[it].percentage)
                },
                count = listOfWords.size
            )
        }
    }

    @Composable
    private fun myModifier(paddingValues: PaddingValues): Modifier{
        return Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_background))
            .padding(paddingValues)
    }

    @SuppressLint("DefaultLocale")
    @Composable
    private fun CardOfStats(w: Word, winRate: Float){

        val ans = WordToTaskMapper().getDisplayableText(w)

        Row(horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp, 3.dp, 8.dp, 3.dp)
                .background(
                    colorResource(id = R.color.dark_background_light),
                    RoundedCornerShape(5.dp)
                )
        ){
            Text(
                text = ans,
                fontSize = 20.sp,
                color = colorResource(id = R.color.light_background),
                modifier = Modifier
                    .padding(8.dp, 3.dp, 8.dp, 3.dp)
                    .width(200.dp)
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

                if(winRate >= 0){
                    Text(
                        color = calculateColor(winRate),
                        text = String.format("%.1f", winRate * 100) + "%",
                        modifier = Modifier
                            .padding(10.dp, 0.dp)
                    )

                }

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
        if(winRate < 0){
            return Color(152,152,152)
        }
        return Color((1F - winRate) * 2, winRate * 2, 0F, alpha = 1F)
    }

}
@Composable
@Preview
private fun Preview(){
    StatsScreenDrawer(PaddingValues(20.dp)).DrawContentScreen(listOfWords = listOf(
        Word("в*век;;___ не забуду", TaskTopic().NARECHI9,1F),
        Word("на голову;;упал снег с ветки|выше", TaskTopic().NARECHI9,0.9F),
        Word("на*право", TaskTopic().NARECHI9,0.2F),
        Word("подобру-поздорову", TaskTopic().NARECHI9,0F),
        Word("на ура;;решение было принято", TaskTopic().NARECHI9,-1F),
        Word("Великий писатель - величественный взгляд", TaskTopic().PARONIM,-1F),
        Word("по*среди", TaskTopic().NARECHI9,-1F),
    ))
}