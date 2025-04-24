package com.example.russian.login.draw.comp

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.russian.R
import com.example.russian.main.theme.LightBlue

@Composable
fun DescriptionDialog(
    onDismissRequest: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: Painter? = null,
) {
    AlertDialog(
        icon = {
            icon?.let {
                Icon(
                    it,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(
                text = dialogText,
                style = MaterialTheme.typography.bodyMedium
            )
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
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    )
}

@Preview
@Composable
private fun DescriptionPreview() {
    DescriptionDialog(
        {},
        "Title",
        "asldjnaskjdasn la \n askdbas kasjdb ksab",
    )
}