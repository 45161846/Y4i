package com.example.russian.architecture2.viewmodel.main

import com.example.russian.architecture2.backend.data.dao.StatsDao
import com.example.russian.architecture2.ui.state.FilterState
import com.example.russian.architecture2.ui.state.StatsScreenState
import kotlinx.coroutines.flow.StateFlow

interface StatsViewModelAPI {

    fun uiState(): StateFlow<StatsScreenState>

    fun uiFilterState(): StateFlow<FilterState>

    fun setDao(statsDao: StatsDao)

    fun setStatsParams(parametersAPI: StatsParametersAPI)
}