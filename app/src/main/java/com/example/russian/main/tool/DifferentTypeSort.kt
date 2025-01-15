package com.example.russian.main.tool

import com.example.russian.architectured.stats.comp.filter.SortDirection
import com.example.russian.architectured.stats.comp.filter.SortType
import com.example.russian.main.back.data.entity.Statistics
import com.example.russian.main.enums.SortTypeMode
import com.example.russian.main.enums.SortTypesEnum

class DifferentTypeSort(
    private val sortType: SortType,
    private val array: List<Statistics>
) {

    fun sort(): List<Statistics>{

        var res = when(sortType){
            is SortType.ALPHABETICAL -> array.sortedBy {
                it.displayableText.lowercase()
            }
            is SortType.BY_WIN_RATE -> array.sortedBy {
                it.winRate()
            }
        }

        if (sortType.direction == SortDirection.DOWN){
            res = res.reversed()
        }
        return res
    }

}