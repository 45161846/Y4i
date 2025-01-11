package com.example.russian.architectured.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults

@Composable
fun GoToButton(
    modifier: Modifier,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colorScheme.primary
        ),
        onClick = onClick,
        shape = RoundedCornerShape(8.dp)
    ) {
        Image(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )
    }
}