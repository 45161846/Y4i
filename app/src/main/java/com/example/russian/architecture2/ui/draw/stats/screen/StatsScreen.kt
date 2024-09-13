package com.example.russian.architecture2.ui.draw.stats.screen

import androidx.compose.runtime.Composable
import com.example.russian.architecture2.ui.actions.MyActions
import com.example.russian.architecture2.ui.state.StatsFirstScreenState

@Composable
fun StatsScreen(state: StatsFirstScreenState, actions: StatsScreenActions){

}

data class StatsScreenActions(
    val search: (String) -> Unit
): MyActions()