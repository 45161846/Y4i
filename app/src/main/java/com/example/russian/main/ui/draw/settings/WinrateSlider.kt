package com.example.russian.main.ui.draw.settings

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WinrateSlider(modifier: Modifier, onValueChange: (Float) -> Unit) {
    var sliderPosition by rememberSaveable { mutableFloatStateOf(0.5f) }

    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        Slider(
            value = sliderPosition,
            onValueChange = {
                onValueChange(it)
                sliderPosition = it
            },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.tertiary,
                activeTrackColor = MaterialTheme.colorScheme.tertiary,
                inactiveTrackColor = MaterialTheme.colorScheme.primary,
            ),
            valueRange = 0f..1f,
            modifier = modifier
        )
    }


}