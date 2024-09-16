package com.example.russian.viewmodel.main

import androidx.navigation.NavController
import com.example.russian.back.data.dao.StatsDao
import com.example.russian.ui.state.FilterScreenData
import com.example.russian.ui.state.StatsFirstScreenState
import kotlinx.coroutines.flow.StateFlow

interface StatsViewModelAPI {
    fun uiState(): StateFlow<StatsFirstScreenState>

    fun uiFilterData(): FilterScreenData

    fun actions(screenType: Any, navController: NavController): com.example.russian.ui.actions.MyActions

    fun setDao(statsDao: StatsDao)

    fun setStatsParams(parametersAPI: StatsParametersAPI)
}