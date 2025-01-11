package com.example.russian.main.viewmodel.main

import androidx.navigation.NavController
import com.example.russian.main.ui.draw.settings.SettingActions
import com.example.russian.main.ui.draw.settings.SettingScreenData
import com.example.russian.main.ui.state.FilterScreenData
import com.example.russian.main.ui.state.StatsFirstScreenState
import kotlinx.coroutines.flow.StateFlow

interface MainViewModelAPI {
    fun uiState(): StateFlow<StatsFirstScreenState>

    fun uiFilterData(): FilterScreenData

    fun uiSettingsScreen(): SettingScreenData
    fun actionsSettingsScreen(): SettingActions

    fun actions(
        screenType: Any,
        navController: NavController
    ): com.example.russian.main.ui.actions.MyActions
}