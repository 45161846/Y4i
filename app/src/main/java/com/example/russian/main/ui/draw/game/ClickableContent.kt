package com.example.russian.main.ui.draw.game

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.russian.main.ui.draw.test.testClickableWord
import com.example.russian.main.ui.draw.test.testWordClickFun
import com.example.russian.main.ui.state.ClickableWord
import com.example.russian.main.ui.theme.LightBlue
import com.example.russian.main.ui.theme.family

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ClickableTextTaskContent(
    words: List<ClickableWord>,
    onWordClick: (Int) -> Unit,
    onAnswered: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
        ,

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        FlowRow(
            Modifier.wrapContentSize().background(Color.Unspecified, RoundedCornerShape(12.dp)).padding(horizontal = 4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Start
        ) {
            words.forEachIndexed { ind, it ->
                WordCell(it) {
                    onWordClick(ind)
                }
            }
        }

    }

}

@Composable
private fun WordCell(word: ClickableWord, modifier: Modifier = Modifier, onClick: () -> Unit) {

    val text = when (word) {
        is ClickableWord.NoClick -> remember(word.text) { mutableStateOf(word.text) }
        is ClickableWord.Clickable -> word.textFlow.collectAsState()
    }
    val interactionSource = remember { MutableInteractionSource() }

    val lineColor = MaterialTheme.colorScheme.primary

    when (word) {
        is ClickableWord.NoClick -> Text(
            text = text.value,
            fontFamily = family,
            fontSize = 18.sp,
            textAlign = TextAlign.Start,
            modifier = modifier
                .padding(vertical = 2.dp)
            ,
            color = MaterialTheme.colorScheme.onSurfaceVariant

        )

        is ClickableWord.Clickable -> Text(
            text = text.value,
            fontFamily = family,
            fontSize = 18.sp,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = LocalTextStyle.current.copy(lineBreak = LineBreak.Simple),
            modifier = Modifier
                .padding(vertical = 2.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { onClick.invoke() }
                .drawBehind {
                    val strokeWidthPx = 1.dp.toPx()
                    val verticalOffset = size.height - 2.sp.toPx()
                    drawLine(
                        color = lineColor,
                        strokeWidth = strokeWidthPx,
                        start = Offset(0f, verticalOffset),
                        end = Offset(size.width, verticalOffset)
                    )
                }
        )
    }

}

@Preview
@Composable
private fun Preview() {
    ClickableTextTaskContent(
        modifier = Modifier,
        words = testClickableWord(),
        onWordClick = testWordClickFun(),
        onAnswered = {}
    )
}