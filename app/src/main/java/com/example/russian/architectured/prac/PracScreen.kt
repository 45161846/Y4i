package com.example.russian.architectured.prac

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.russian.architectured.Id
import com.example.russian.architectured.prac.remote.PracRemoteScreen
import com.example.russian.main.ui.draw.practice.DrawPracticeContent
import com.example.russian.main.ui.draw.practice.PracRemoteContent

@Composable
fun PracScreen(
    viewModel: PracViewModel,
    showLocal: Boolean,
    startGameActivity: (Id) -> Unit
) {

    val localState = viewModel.pracScreenLocalUiState.collectAsStateWithLifecycle()
    val remoteState = viewModel.pracScreenRemoteUiState.collectAsStateWithLifecycle()


    AnimatedVisibility(
        showLocal,
        enter = slideInHorizontally {
            -it
        },
        exit = slideOutHorizontally { -it },
    ) {
        DrawPracticeContent(
            localState.value,
            onMove = viewModel::onMovePlaylist,
            onClick = startGameActivity
            )
    }
    AnimatedVisibility(
        showLocal.not(),
        enter = slideInHorizontally {
            it
        },
        exit = slideOutHorizontally { it },
    ) {
        PracRemoteScreen()
    }

}
