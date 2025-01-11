package com.example.russian.main.ui.route

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.russian.architectured.MainNavDestinations
import com.example.russian.main.ui.draw.DefaultScaffold
import com.example.russian.main.ui.draw.PracTopBar
import com.example.russian.main.ui.draw.Source
import com.example.russian.main.ui.draw.practice.DrawPracticeContent
import com.example.russian.main.ui.draw.practice.PracRemoteContent
import com.example.russian.main.ui.state.PracScreenStage
import com.example.russian.main.viewmodel.main.MainViewModel

@Composable
fun PracRoute(
    viewModel: MainViewModel = hiltViewModel(),
    bottomBarClick: (MainNavDestinations) -> Unit,
    startGameActivity: (Long) -> Unit
) {

    val data = viewModel.pracScreenLocalUiState.collectAsState()
    val actions by remember(0){
        mutableStateOf(viewModel.pracActions)
    }
    actions.onPlaylistClick = startGameActivity

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
                    .background(MaterialTheme.colorScheme.surface)
            ) { }

            is PracScreenStage.Content -> {

                DefaultScaffold(
                    selectedItemIndex = MainNavDestinations.Prac,
                    changeSelectedItemIndex = bottomBarClick,
                    topBar = {
                        PracTopBar(
                            Modifier
                                .background(
                                    MaterialTheme.colorScheme.primary, RoundedCornerShape(
                                        topStart = 0.dp, topEnd = 0.dp, bottomEnd = 16.dp, bottomStart = 16.dp
                                    )
                                )
                                .padding(top = 16.dp, bottom = 8.dp),
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