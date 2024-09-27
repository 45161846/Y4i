package com.example.russian.ui.draw.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.russian.ui.theme.OnSecondaryDark
import com.example.russian.ui.theme.family

@Composable
fun SettingsParagraph(
    name: String, content: @Composable
        () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(12.dp)
    ) {
        Text(
            name,
            fontFamily = family,
            fontSize = 16.sp,
            color = Color.White
        )
        Spacer(Modifier.fillMaxWidth().height(4.dp))

        content.invoke()
    }
}