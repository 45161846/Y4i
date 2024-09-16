package com.example.russian.activity

import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.russian.R
import com.example.russian.application.MyApplication
import com.example.russian.viewmodel.game.GameViewModel
import com.example.russian.viewmodel.game.GameViewModelAPI
import com.example.russian.viewmodel.game.NecessaryData
import com.example.russian.tool.VibrationAPI
import com.example.russian.ui.draw.game.StateDrawer
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
            application = application as MyApplication,
            vibrationAPI =  object : VibrationAPI {
                val vibrator = application.getSystemService(VIBRATOR_SERVICE) as Vibrator

                @RequiresApi(Build.VERSION_CODES.O)
                val effectWrong: VibrationEffect = VibrationEffect.createOneShot(350, VibrationEffect.DEFAULT_AMPLITUDE)
                @RequiresApi(Build.VERSION_CODES.Q)
                val effectCorrect: VibrationEffect = VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK)

                @RequiresApi(Build.VERSION_CODES.S)
                override fun vibrateAnswerWrong() {
                    vibrator.vibrate(effectWrong)
                }

                override fun vibrateCorrect() {
                    if(Build.VERSION.SDK_INT >= 29){
                        vibrator.vibrate(effectCorrect)
                    }
                }
            }
        )

        viewmodel.setNecessaryData(necessaryData)

        setContent{

            window.statusBarColor = PrimaryBackground.toArgb()
            window.navigationBarColor = PrimaryBackground.toArgb()

            val state = viewmodel.uiStateFlow().collectAsStateWithLifecycle()

            StateDrawer.Screen(taskState = state.value)
        }


    }
}