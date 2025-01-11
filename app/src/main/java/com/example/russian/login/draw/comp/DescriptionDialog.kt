package com.example.russian.login.draw.comp

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import com.example.russian.R
import com.example.russian.main.ui.theme.LightBlue

@Composable
fun DescriptionDialog(
    onDismissRequest: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: Painter? = null,
) {
    AlertDialog(
        containerColor = Color.DarkGray,
        icon = {
            icon?.let {
                Icon(
                    it,
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {},
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(
                    stringResource(R.string.close_description_dialog),
                    color = LightBlue
                )
            }
        }
    )
}