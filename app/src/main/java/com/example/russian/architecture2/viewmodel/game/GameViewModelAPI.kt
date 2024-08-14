package com.example.russian.architecture2.viewmodel.game

import com.example.russian.architecture2.application.MyApplication
import com.example.russian.architecture2.ui.state.TaskUIState
import kotlinx.coroutines.flow.StateFlow

interface GameViewModelAPI {

    fun uiStateFlow(): StateFlow<TaskUIState>

    fun setNecessaryData(data: NecessaryData)

}

data class NecessaryData(
    val application: MyApplication,
    val playlistId: Long
)