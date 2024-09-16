package com.example.russian.ui.draw.game

import androidx.compose.runtime.Composable
import com.example.russian.ui.state.hood.HoodStateInterface
import com.example.russian.tasks.TaskInterface

interface DrawerInterface {

    @Composable
    fun Screen(
        task: TaskInterface?,
        hood: HoodStateInterface,
        onAnswered: (Boolean) -> Unit
    )

}