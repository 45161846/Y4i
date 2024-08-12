package com.example.russian.gameClasses.activity.draw

import androidx.compose.runtime.Composable
import com.example.russian.gameClasses.viewmodel.hood.HoodStateInterface
import com.example.russian.tasks.TaskInterface

public interface DrawerInterface {

    @Composable
    fun Screen(
        task: TaskInterface?,
        hood: HoodStateInterface,
        onAnswered: (Boolean) -> Unit
    )

}