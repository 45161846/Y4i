package com.example.russian.ui.draw.stats.comp

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.russian.ui.theme.family

@Composable
fun MyFilterOptionText(text: String){
    val optionsFontSize = 18.sp
    val textColor = Color.White

    Text(
        text = text,
        fontSize = optionsFontSize,
        fontFamily = family,
        color = textColor
    )

}