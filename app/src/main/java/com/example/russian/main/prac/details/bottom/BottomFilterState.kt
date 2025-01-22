package com.example.russian.main.prac.details.bottom

import com.example.russian.main.stats.comp.filter.PercentageBounds
import com.example.russian.main.stats.comp.filter.SortDirection
import com.example.russian.main.stats.comp.filter.SortType

data class BottomFilterState(

    val sortType: SortType,
    val bounds: PercentageBounds,
    val showUnanswered: Boolean,

){
    companion object{
        fun test() = BottomFilterState(
            SortType.ALPHABETICAL(SortDirection.DOWN),
            PercentageBounds(20, 95),
            true
        )
    }
}

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