package com.example.russian.main.ui.draw.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import com.example.russian.main.ui.theme.Black

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
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isChecked) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary
        ),
        onClick = {
            isChecked = isChecked.not()
            onClick.invoke(isChecked)
        },
        shape = RoundedCornerShape(8.dp)
    ) {
        Image(
            imageVector = icon,
            colorFilter = ColorFilter.tint(if(!isChecked) MaterialTheme.colorScheme.onPrimary else Black),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )
    }
}