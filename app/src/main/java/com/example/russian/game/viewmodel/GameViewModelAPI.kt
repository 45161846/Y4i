package com.example.russian.game.viewmodel

import com.example.russian.main.Id
import com.example.russian.game.tool.SoundAPI
import com.example.russian.game.tool.VibrationAPI
import com.example.russian.game.ui.state.TaskUIState
import kotlinx.coroutines.flow.StateFlow

interface GameViewModelAPI {

    fun uiStateFlow(): StateFlow<TaskUIState>

    fun setNecessaryData(data: NecessaryData)

}

data class NecessaryData(
    val playlistId: Id,
    val vibrationAPI: VibrationAPI,
    val soundAPI: SoundAPI,
)