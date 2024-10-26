package com.example.russian.ui.route

import android.view.Window
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.russian.ui.draw.DefaultScaffold
import com.example.russian.ui.draw.PracTopBar
import com.example.russian.ui.draw.Source
import com.example.russian.ui.draw.practice.DrawPracticeContent
import com.example.russian.ui.draw.practice.PracRemoteContent
import com.example.russian.ui.state.PracDestination
import com.example.russian.ui.state.PracScreenStage
import com.example.russian.ui.theme.PrimaryBackground
import com.example.russian.ui.theme.SecondaryBackground
import com.example.russian.viewmodel.main.MainViewModel

@Composable
fun PracRoute(
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel(),
    bottomBarClick: (Int) -> Unit,
    window: Window,
    startGameActivity: (Long) -> Unit
) {

    val data = viewModel.pracScreenLocalUiState.collectAsState()
    val actions by remember(0){
        mutableStateOf(viewModel.pracActions)
    }
    actions.onPlaylistClick = startGameActivity
    window.statusBarColor = SecondaryBackground.toArgb()
    window.navigationBarColor = SecondaryBackground.toArgb()


    val localState = viewModel.pracScreenLocalUiState.collectAsStateWithLifecycle()
    val remoteState = viewModel.pracScreenRemoteUiState.collectAsStateWithLifecycle()

    var showLocal by remember{
        mutableStateOf(viewModel.currentScreen == Source.Local)
    }

    data.value.let { pracStage ->
        when (pracStage) {
            is PracScreenStage.Loading -> Box(
                Modifier
                    .fillMaxSize()
                    .background(PrimaryBackground)
            ) { }

            is PracScreenStage.Content -> {

                DefaultScaffold(
                    selectedItemIndex = 1,
                    changeSelectedItemIndex = bottomBarClick,
                    topBar = {
                        PracTopBar(
                            onLocalClick = {
                                if(!showLocal){
                                    showLocal = true
                                }
                            },
                            onRemoteClick = {
                                if(showLocal){
                                    showLocal = false
                                }
                            }
                        )
                    },
                    displayableUI = { padding ->

                        AnimatedVisibility(
                            showLocal,
                            enter = slideInHorizontally{
                                -it
                            },
                            exit = slideOutHorizontally { -it },
                        ){
                            DrawPracticeContent(padding, localState.value, actions)
                        }
                        AnimatedVisibility(
                            showLocal.not(),
                            enter = slideInHorizontally{
                                it
                            },
                            exit = slideOutHorizontally { it },
                        ){
                            remoteState.value.let {
                                if(it is PracScreenStage.Content.PracScreenRemote){
                                    PracRemoteContent(it, padding)
                                }
                            }
                        }
                    }
                )
            }
        }


    }


//    val screenData = viewModel.pracScreenUiState.collectAsState()
//    val actions by remember(1) {
//        mutableStateOf(viewModel.pracActions)
//    }
//
//    window.statusBarColor = PrimaryBackground.toArgb()
//
//    DefaultScaffold(
//        changeSelectedItemIndex = bottomBarClick,
//        selectedItemIndex = 1,
//        displayableUI = {
//            DrawPracticeContent(it, screenData.value, actions)
//        }
//    )
}