package com.example.russian.main.prac.details.bottom

import androidx.compose.runtime.Stable
import com.example.russian.main.stats.comp.filter.PercentageBounds
import com.example.russian.main.stats.comp.filter.SortDirection
import com.example.russian.main.stats.comp.filter.SortType

@Stable
data class BottomFilterState(

    val sortType: SortType,
    val bounds: PercentageBounds,
    val showUnanswered: Boolean,

){
    companion object{
        fun default() = BottomFilterState(
            SortType.ALPHABETICAL(SortDirection.DOWN),
            PercentageBounds(0, 100),
            true
        )
    }
}

@Stable
data class BottomFilterActions(

    val onSortTypeClick: (SortType) -> Unit,
    val onShowUnanswered: () -> Unit,
    val onBoundsChange: (PercentageBounds) -> Unit

){
    companion object{
        fun test() = BottomFilterActions(
            {},{},{}
        )
    }
}