package com.example.russian.main.ui.draw.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.russian.main.ui.theme.CommonTypography

@Composable
fun Counter(text: String, backColor: Color) {
    RoundTextField(text = text, backColor = backColor)
}

@Composable
private fun RoundTextField(
    text: String,
    backColor: Color,
    style: TextStyle = CommonTypography.bodyLarge
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