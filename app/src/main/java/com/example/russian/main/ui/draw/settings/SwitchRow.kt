package com.example.russian.main.ui.draw.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.russian.main.ui.draw.common.MySwitch
import com.example.russian.main.ui.draw.stats.comp.MyFilterOptionText

@Composable
fun SwitchRow(modifier: Modifier, state: com.example.russian.main.ui.draw.settings.SwitchState, onCheckChange: (Boolean) -> Unit){
    Row (
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){

        val checked = state.checked.collectAsState()

        MyFilterOptionText(state.text)

        MySwitch(Modifier.size(64.dp), isChecked =  if (checked.value) 1 else 0) {
            onCheckChange(it == 1)
        }

    }
}