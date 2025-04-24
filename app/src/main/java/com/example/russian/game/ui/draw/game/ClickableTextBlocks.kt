package com.example.russian.game.ui.draw.game

import android.widget.EditText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString

@Composable
fun ClickableTextBlocks(
    text: AnnotatedString,
    onClick: (Int) -> Unit,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    modifier: Modifier = Modifier
) {

    var currStyle by remember {
        mutableStateOf(style)
    }

    CompositionLocalProvider(LocalTextInputService provides null) {
        TextField(
            value = text.text,
            onValueChange = {},
            textStyle = currStyle,
            modifier = modifier,
        )
    }

    buildAnnotatedString {
        
    }
}