package com.example.russian.ui.draw.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import com.example.russian.ui.theme.FiltersScreenButtonActive
import com.example.russian.ui.theme.ThirdBackground

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
            backgroundColor = if (isChecked) FiltersScreenButtonActive else ThirdBackground
        ),
        onClick = {
            isChecked = isChecked.not()
            onClick.invoke(isChecked)
        },
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