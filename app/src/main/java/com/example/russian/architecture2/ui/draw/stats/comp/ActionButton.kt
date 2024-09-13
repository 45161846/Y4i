package com.example.russian.architecture2.ui.draw.stats.comp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.russian.R
import com.example.russian.ui.theme.SecondaryBackground
import com.example.russian.ui.theme.family

@Composable
fun ActionButton(text: String, color: Color, onClick: () -> Unit){

    val textColor = SecondaryBackground

    Button(
        modifier = Modifier
            .padding(bottom = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        ),
        onClick = onClick,
    ) {
        Text(
            text,
            fontSize = 20.sp,
            fontFamily = family,
            color = textColor
        )
    }
}

@Preview
@Composable
private fun Preview(){
    ActionButton(stringResource(id = R.string.filter_screen_reset_text), colorResource(R.color.reset_button)) {  }
}