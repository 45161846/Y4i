package com.example.russian.viewmodel.game

import android.media.MediaPlayer
import com.example.russian.application.MyApplication
import com.example.russian.tool.SoundAPI
import com.example.russian.tool.VibrationAPI
import com.example.russian.ui.state.TaskUIState
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