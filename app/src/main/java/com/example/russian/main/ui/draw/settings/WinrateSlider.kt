package com.example.russian.main.ui.draw.settings

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WinrateSlider(modifier: Modifier, winrate: Float, onValueChange: (Float) -> Unit) {

    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        Slider(
            value = winrate,
            onValueChange = onValueChange,
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.secondaryContainer,
                activeTrackColor = MaterialTheme.colorScheme.primaryContainer,
                inactiveTrackColor = MaterialTheme.colorScheme.onSecondaryContainer,
            ),
            valueRange = 0f..1f,
            modifier = modifier
        )
    }


}