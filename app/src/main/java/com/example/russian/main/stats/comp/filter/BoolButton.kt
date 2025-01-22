package com.example.russian.main.stats.comp.filter

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.russian.R

@Composable
fun BoolButton(
    checked: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){

    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor =
            if (checked) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Text(
            text = stringResource(R.string.filter_show_unanswered),
            style = MaterialTheme.typography.bodyMedium.copy(
                color =
                if (checked) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.onSurfaceVariant
            ),
        )
    }
}