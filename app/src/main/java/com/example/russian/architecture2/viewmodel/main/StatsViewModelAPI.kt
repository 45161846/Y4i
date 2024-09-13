package com.example.russian.architecture2.viewmodel.main

import androidx.navigation.NavController
import com.example.russian.MyEnumClasses.FilterAPI
import com.example.russian.architecture2.backend.data.dao.StatsDao
import com.example.russian.architecture2.ui.actions.MyActions
import com.example.russian.architecture2.ui.draw.stats.screen.FilterScreenState
import com.example.russian.architecture2.ui.state.FilterScreenData
import com.example.russian.architecture2.ui.state.StatsFirstScreenState
import kotlinx.coroutines.flow.StateFlow

interface StatsViewModelAPI {
    fun uiState(): StateFlow<StatsFirstScreenState>

    fun uiFilterData(): FilterScreenData

    fun actions(screenType: Any, navController: NavController): MyActions

    fun setDao(statsDao: StatsDao)

    fun setStatsParams(parametersAPI: StatsParametersAPI)
}