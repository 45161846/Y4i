package com.example.russian.gameClasses.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.russian.R
import com.example.russian.architecture2.application.MyApplication
import com.example.russian.architecture2.viewmodel.game.GameViewModel
import com.example.russian.architecture2.viewmodel.game.GameViewModelAPI
import com.example.russian.architecture2.viewmodel.game.NecessaryData
import com.example.russian.gameClasses.activity.draw.StateDrawer
import com.example.russian.ui.theme.PrimaryBackground

class GameActivity : ComponentActivity() {

    private val viewmodel: GameViewModelAPI by viewModels<GameViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val key = getString(R.string.game_activity_start_topic_key)

        val playlistId =
            intent.extras?.getLong(key) ?: throw RuntimeException("No playlistId given")

        val necessaryData = NecessaryData(
            playlistId = playlistId,
            application = application as MyApplication
        )

        viewmodel.setNecessaryData(necessaryData)

        setContent{

            window.statusBarColor = PrimaryBackground.toArgb()
            window.navigationBarColor = PrimaryBackground.toArgb()

            val state = viewmodel.uiStateFlow().collectAsStateWithLifecycle(lifecycle)

            StateDrawer.Screen(taskState = state.value)
        }


    }
}