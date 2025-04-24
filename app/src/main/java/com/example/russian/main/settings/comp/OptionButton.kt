package com.example.russian.main.settings.comp

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun TestCardOptionButton(
    modifier: Modifier,
    icon: ImageVector,
    checked: Boolean,
    onClick: (Boolean) -> Unit
) {
    var isChecked by rememberSaveable {
        mutableStateOf(checked)
    }

    Button(
        modifier = modifier
            .border(
                2.dp,
                if (isChecked) MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.73f
                ) else MaterialTheme.colorScheme.surfaceVariant,
                RoundedCornerShape(20)
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        onClick = {
            isChecked = isChecked.not()
            onClick.invoke(isChecked)
        },
        shape = RoundedCornerShape(20)
    ) {
        Image(
            imageVector = icon,
            colorFilter = ColorFilter.tint(
                if (isChecked) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.surfaceVariant
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}