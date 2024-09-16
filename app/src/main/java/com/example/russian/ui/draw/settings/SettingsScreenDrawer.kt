package com.example.russian.ui.draw.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.russian.R

@Composable
fun DrawSettingsContent(paddingValues: PaddingValues) {
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
        Text(color = Color.White,
            text = "Settings",
        )
    }
}