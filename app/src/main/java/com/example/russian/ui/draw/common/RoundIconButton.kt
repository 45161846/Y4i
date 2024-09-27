package com.example.russian.ui.draw.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import com.example.russian.ui.theme.ThirdBackground

@Composable
fun RoundIconButton(
    modifier: Modifier,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = ThirdBackground
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