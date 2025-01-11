package com.example.russian.main.ui.route

import android.view.Window
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.russian.architectured.MainNavDestinations
import com.example.russian.main.ui.draw.DefaultScaffold
import com.example.russian.main.ui.draw.settings.DrawSettingsContent
import com.example.russian.main.viewmodel.main.MainViewModel

@Composable
fun SettingsRoute(
    viewModel: MainViewModel = hiltViewModel(),
    bottomBarClick: (MainNavDestinations) -> Unit,
) {

    val settingScreenData = viewModel.uiSettingsScreen()
    val actions by remember(1) {
        mutableStateOf(viewModel.actionsSettingsScreen())
    }



    DefaultScaffold(
        changeSelectedItemIndex = bottomBarClick,
        selectedItemIndex = MainNavDestinations.Settings,
        topBar =  {},
        displayableUI = {
            DrawSettingsContent(settingScreenData, actions, it)
        }
    )
}