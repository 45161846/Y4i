package com.example.russian.architecture2.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.russian.architecture2.backend.data.dao.StatsDao
import com.example.russian.architecture2.mapper.WordMapper
import com.example.russian.architecture2.repository.arch.StatsScreenRepositoryInterface
import com.example.russian.architecture2.ui.state.FilterState
import com.example.russian.architecture2.ui.state.StatsScreenState
import com.example.russian.ui.theme.PrimaryBackground
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.launch

class StatsViewModel : ViewModel(), StatsViewModelAPI {

    private val _uiState = MutableStateFlow<StatsScreenState>(StatsScreenState.Loading)
    private val uiState: StateFlow<StatsScreenState> = _uiState

    private val _filterState = MutableStateFlow(FilterState.Default)
    private val filterState: StateFlow<FilterState> = _filterState

    private lateinit var repo: StatsScreenRepositoryInterface

    private lateinit var statsParams: StatsParametersAPIImpl

    override fun uiState(): StateFlow<StatsScreenState> = uiState
    override fun uiFilterState(): StateFlow<FilterState> = filterState

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun setDao(statsDao: StatsDao) {
        repo.setDao(statsDao)

        viewModelScope.launch {
            _filterState
                .flatMapMerge {
                    repo.wordsFiltered(it)
                }
                .collect { words ->
                    if (words.isEmpty()) {
                        _uiState.value = StatsScreenState.NothingFound
                    } else {
                        _uiState.value = StatsScreenState.Success(
                            WordMapper.wordListToCards(words, statsParameters()),
                            PrimaryBackground,
                            onSearch = { pref ->
                                search(pref)
                            }
                        )
                    }
                }
        }
    }


    private fun search(pref: String) {

    }


    private fun statsParameters(): StatsParametersAPI = this.statsParams
    override fun setStatsParams(parametersAPI: StatsParametersAPI) {
        this.statsParams = StatsParametersAPIImpl(
            parametersAPI.showWinRate(),
            parametersAPI.showWinRateIndicator()
        )
    }
}

interface StatsParametersAPI {
    fun showWinRate(): Boolean
    fun showWinRateIndicator(): Boolean
}

private class StatsParametersAPIImpl(
    private var showWinRate: Boolean,
    private var showWinRateIndicator: Boolean
): StatsParametersAPI{
    override fun showWinRate(): Boolean = showWinRate

    override fun showWinRateIndicator(): Boolean  = showWinRateIndicator
}