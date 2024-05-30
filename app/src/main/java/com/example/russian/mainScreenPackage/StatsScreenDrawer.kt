package com.example.russian.mainScreenPackage

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.russian.MyEnumClasses.TaskTopic
import com.example.russian.database.Word
import com.example.russian.mainScreenPackage.screenDrawers.StatsScreenDrawer

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