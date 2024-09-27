package com.example.russian.viewmodel.main

import androidx.navigation.NavController
import com.example.russian.application.MyApplication
import com.example.russian.ui.draw.settings.SettingActions
import com.example.russian.ui.draw.settings.SettingScreenData
import com.example.russian.ui.state.FilterScreenData
import com.example.russian.ui.state.StatsFirstScreenState
import kotlinx.coroutines.flow.StateFlow

interface StatsViewModelAPI {
    fun uiState(): StateFlow<StatsFirstScreenState>

    fun uiFilterData(): FilterScreenData

    fun uiSettingsScreen(): SettingScreenData
    fun actionsSettingsScreen(): SettingActions

    fun actions(
        screenType: Any,
        navController: NavController
    ): com.example.russian.ui.actions.MyActions

    fun setDao(application: MyApplication)
}