package com.example.russian.main.ui.draw.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.russian.architectured.settings.comp.CustomSwitch
import com.example.russian.main.ui.draw.stats.comp.MyFilterOptionText

@Composable
fun SwitchRow(modifier: Modifier, state: SwitchState, onCheckChange: (Boolean) -> Unit){
    Row (
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){

        val checked by state.checked.collectAsState()

        MyFilterOptionText(state.text)

        CustomSwitch (
            initialChecked = checked,
            height = 32.dp
        ) {
            onCheckChange(it)
        }

    }
}