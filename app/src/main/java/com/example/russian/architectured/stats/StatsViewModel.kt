package com.example.russian.architectured.stats

import androidx.compose.ui.util.fastMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.russian.R
import com.example.russian.architectured.TaskType
import com.example.russian.architectured.data.local.db.repo.StatsRepositoryApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    repo: StatsRepositoryApi
): ViewModel() {

    private val _stats = repo.allStats()
    private val _filterParams = flowOf(
        StatsParams(
            showPercent = true,
            showLine = true,
            showIcon = true
        )
    )

    val uiState: StateFlow<StatsScreenState> = combine(_stats, _filterParams){stats, filter ->
        StatsScreenState.UI(
            stats.fastMap {
                CardUIData(
                    text = it.displayText,
                    winRate = it.correct.toDouble() / (it.correct + it.incorrect).toDouble(),
                    hasBeenAnswered = it.correct + it.incorrect > 0,
                    themeIconId = iconId(it.taskType),
                    params = filter
                )
            }
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = StatsScreenState.Loading
        )

    fun search(pref: String){

    }

    private fun iconId(taskType: TaskType): Int{
        return when(taskType){
            TaskType.YDAR -> R.drawable.ydar_icon
            else -> R.drawable.paromins_icon
        }
    }

}