package com.example.russian.main.stats.comp.filter

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ParagraphView(title: String, content: @Composable () -> Unit) {

    TitleText(title)
    content()

}


@Composable
private fun TitleText(title: String) {

    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .fillMaxWidth()
    )

}