package com.example.russian.main.stats.comp.filter

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResetButton(
    text: String,
    onClick: () -> Unit
) {

    val buttonColor = MaterialTheme.colorScheme.secondaryContainer

    Button(
        modifier = Modifier
            .padding(bottom = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor
        ),
        onClick = onClick,
    ) {
        Text(
            text,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}