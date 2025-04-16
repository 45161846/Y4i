package com.example.russian.main.custom

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun TextAutoFill(
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    modifier: Modifier = Modifier,
    color: Color = style.color
) {

    var currStyle by remember {
        mutableStateOf(style)
    }

    Text(
        text = text,
        style = currStyle,
        softWrap = false,
        color = color,
        modifier = modifier,
        onTextLayout = { res ->
            if (res.didOverflowWidth || res.didOverflowHeight){
                val size = currStyle.fontSize
                currStyle = currStyle.copy(fontSize = size * 0.95)
            }
        }
    )

}