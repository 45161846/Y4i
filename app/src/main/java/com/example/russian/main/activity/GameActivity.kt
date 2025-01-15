package com.example.russian.main.activity

import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.russian.R
import com.example.russian.architectured.Id
import com.example.russian.main.tool.SoundAPI
import com.example.russian.main.tool.VibrationAPI
import com.example.russian.main.ui.draw.game.StateDrawer
import com.example.russian.main.ui.theme.RussianTheme
import com.example.russian.main.viewmodel.game.GameViewModel
import com.example.russian.main.viewmodel.game.GameViewModelAPI
import com.example.russian.main.viewmodel.game.NecessaryData
import com.example.russian.main.viewmodel.main.DisplaySettings
import dagger.hilt.android.AndroidEntryPoint
import kotlin.enums.enumEntries


@AndroidEntryPoint
class GameActivity : ComponentActivity() {
    private val sharedPreferencesKey = "Y4i_shared_preferences"

    private lateinit var vibrationAPI: VibrationAPI
    private lateinit var soundAPI: SoundAPI
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val key = getString(R.string.game_activity_start_topic_key)

        val playlistId =
            Id(intent.extras?.getLong(key) ?: throw RuntimeException("No playlistId given"))

        val settings = DisplaySettings(
            application.getSharedPreferences(sharedPreferencesKey, MODE_PRIVATE)
        )

        vibrationAPI = object : VibrationAPI {
            val vibrator = application.getSystemService(VIBRATOR_SERVICE) as Vibrator

            @RequiresApi(Build.VERSION_CODES.O)
            val effectWrong: VibrationEffect = VibrationEffect.createOneShot(350, VibrationEffect.DEFAULT_AMPLITUDE)
            @RequiresApi(Build.VERSION_CODES.Q)
            val effectCorrect: VibrationEffect = VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK)

            @RequiresApi(Build.VERSION_CODES.S)
            override fun vibrateAnswerWrong() {
                if(settings.vibrationOn){
                    vibrator.vibrate(effectWrong)
                }
            }

            override fun vibrateCorrect() {
                if(Build.VERSION.SDK_INT >= 29 && settings.vibrationOn){
                    vibrator.vibrate(effectCorrect)
                }
            }
        }

        soundAPI = object : SoundAPI {
            override fun stopPLaying(mediaPlayer: MediaPlayer?) {
                if(settings.soundOn){
                    mediaPlayer?.let {
                        mediaPlayer.stop()
                        mediaPlayer.release()
                        this@GameActivity.mediaPlayer = null
                    }
                }
            }

            override fun playAnswerCorrect() {
                if(settings.soundOn) {
                    this.stopPLaying(this@GameActivity.mediaPlayer)
                    this@GameActivity.mediaPlayer =
                        MediaPlayer.create(this@GameActivity, R.raw.correct_answer_sound)
                    this@GameActivity.mediaPlayer?.start()
                }
            }

            override fun playAnswerIncorrect() {
                if(settings.soundOn) {
                    this.stopPLaying(this@GameActivity.mediaPlayer)
                    this@GameActivity.mediaPlayer =
                        MediaPlayer.create(this@GameActivity, R.raw.wrong_answer_sound)
                    this@GameActivity.mediaPlayer?.start()
                }
            }
        }

        val necessaryData = NecessaryData(
            playlistId = playlistId,
            vibrationAPI = vibrationAPI,
            soundAPI = soundAPI,
        )

        enableEdgeToEdge()
        setContent{
            val viewmodel: GameViewModelAPI = hiltViewModel<GameViewModel>()
            viewmodel.setNecessaryData(necessaryData)

            val state = viewmodel.uiStateFlow().collectAsStateWithLifecycle()
            RussianTheme {
                StateDrawer.Screen(taskState = state.value)
            }
        }
    }
}