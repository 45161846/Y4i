package com.example.russian.main.viewmodel.game

import com.example.russian.main.application.MyApplication
import com.example.russian.main.tool.SoundAPI
import com.example.russian.main.tool.VibrationAPI
import com.example.russian.main.ui.state.TaskUIState
import kotlinx.coroutines.flow.StateFlow

interface GameViewModelAPI {

    fun uiStateFlow(): StateFlow<TaskUIState>

    fun setNecessaryData(data: NecessaryData)

}

data class NecessaryData(
    val application: MyApplication,
    val playlistId: Long,
    val vibrationAPI: VibrationAPI,
    val soundAPI: SoundAPI,
)