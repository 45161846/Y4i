package com.example.russian.gameClasses.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.example.russian.R
import com.example.russian.architecture.CustomApplication
import com.example.russian.architecture.data.entity.WordWithSpellings
import com.example.russian.gameClasses.activity.draw.GameActivityDrawer
import com.example.russian.gameClasses.activity.draw.StateDrawer
import com.example.russian.gameClasses.viewmodel.Answer
import com.example.russian.gameClasses.viewmodel.GameViewModelArch
import com.example.russian.gameClasses.viewmodel.GameViewModelImpl
import kotlinx.coroutines.launch

class GameActivity : ComponentActivity() {

    private var viewmodel: GameViewModelImpl? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val key = getString(R.string.game_activity_start_topic_key)

        val playlistId = intent.extras?.getInt(key) ?: throw RuntimeException("No playlistId given")

        viewmodel ?: application.let {app ->
            if (app is CustomApplication) {
                viewmodel = GameViewModelImpl(app, playlistId)
            } else {
                throw RuntimeException("Need custom application to create this viewmodel")
            }
        }

        viewmodel?.let { viewmodel ->

            setContent {
//                var task by remember {
//                    mutableStateOf(viewmodel.currentTask.value)
//                }
                val state = viewmodel.taskUiState.collectAsState()
                
//                viewmodel.currentTask.observe(this) {
//                    task = it
//                }

//                GameActivityDrawer.Screen(
//                    task = task,
//                    hood = viewmodel.getHood(),
//                    onAnswered = { isCorrect ->
//                        viewmodel.answer(object : Answer {
//                            override fun isCorrect(wordWithSpelling: WordWithSpellings): Boolean {
//                                return isCorrect
//                            }
//                        })
//                    }
//                )
                
                StateDrawer.Screen(taskState = state.value)
            }

        } ?: throw RuntimeException("Viewmodel (GameViewModel) is null some how")


    }
}