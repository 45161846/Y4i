package com.example.russian.main.settings.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.russian.main.custom.CustomSwitch

@Composable
fun SwitchRow(
    modifier: Modifier,
    checked: Boolean,
    text: String,
    onCheckChange: (Boolean) -> Unit
) {

    Row(
        modifier = modifier
            .padding(vertical = 12.dp, horizontal = 8.dp)
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text,
            style = MaterialTheme.typography.bodyMedium
        )

        CustomSwitch(
            checked,
            32.dp,
            onCheckChange = onCheckChange
        )

    }
}