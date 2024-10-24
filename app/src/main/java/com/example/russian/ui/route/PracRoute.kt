package com.example.russian.ui.route

import android.view.Window
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.russian.ui.draw.DefaultScaffold
import com.example.russian.ui.draw.practice.DrawPracticeContent
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

    val data = viewModel.pracScreenUiState.collectAsState()
    val actions = viewModel.pracActions
    actions.onPlaylistClick = startGameActivity
    window.statusBarColor = PrimaryBackground.toArgb()
    window.navigationBarColor = SecondaryBackground.toArgb()

    data.value.let {
        when (it) {
            is PracScreenStage.Loading -> Box(
                Modifier
                    .fillMaxSize()
                    .background(PrimaryBackground)
            ) { }

            is PracScreenStage.PracScreenState -> {


                    DefaultScaffold(
                        selectedItemIndex = 1,
                        changeSelectedItemIndex = bottomBarClick,
                        displayableUI = { padding ->
                            DrawPracticeContent(padding, data.value, actions)
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