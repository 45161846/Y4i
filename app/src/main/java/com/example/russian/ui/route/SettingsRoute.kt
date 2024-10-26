package com.example.russian.ui.route

import android.view.Window
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.russian.ui.draw.DefaultScaffold
import com.example.russian.ui.draw.settings.DrawSettingsContent
import com.example.russian.viewmodel.main.MainViewModel

@Composable
fun SettingsRoute(
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel(),
    bottomBarClick: (Int) -> Unit,
    window: Window,
) {

    val settingScreenData = viewModel.uiSettingsScreen()
    val actions by remember(1) {
        mutableStateOf(viewModel.actionsSettingsScreen())
    }



    DefaultScaffold(
        changeSelectedItemIndex = bottomBarClick,
        selectedItemIndex = 0,
        topBar =  {},
        displayableUI = {
            DrawSettingsContent(settingScreenData, actions, it)
        }
    )
}