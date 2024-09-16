package com.example.russian.ui.draw.stats.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.russian.ui.draw.test.testPlaylistState
import com.example.russian.ui.theme.OnSecondary2
import com.example.russian.ui.theme.OnSecondary3
import com.example.russian.ui.theme.SecondaryBackground

private val roundShape = RoundedCornerShape(10.dp)

private val color = SecondaryBackground

@Composable
fun ParagraphView(title: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .background(color, roundShape)
    ) {
        TitleText(title)
        content()
    }
}


@Composable
private fun TitleText(title: String) {

    val textColor = OnSecondary2
    val textSize = 24.sp

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Spacer(
            modifier = Modifier
                .weight(1F)
        )

        Text(
            text = title,
            color = textColor,
            fontSize = textSize,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        )
        Spacer(
            modifier = Modifier
                .weight(1F)
        )
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(OnSecondary3)
            .padding(horizontal = 10.dp)
    )
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
    )
}

@Preview
@Composable
private fun ParagraphPreview() {
    val modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(horizontal = 12.dp)

    ParagraphView("Paragraph 1") {
        PlaylistView(testPlaylistState(), modifier, {})
    }
}