package com.example.russian.main.tool

import com.example.russian.main.back.data.entity.Statistics
import com.example.russian.main.enums.SortType
import com.example.russian.main.enums.SortTypeMode
import com.example.russian.main.enums.SortTypesEnum

class DifferentTypeSort(
    private val sortType: SortType,
    private val array: List<Statistics>
) {

    fun sort(): List<Statistics>{

        if(sortType.mode == SortTypeMode.UNSPECIFIED){
            throw IllegalArgumentException("Illegal mode of sort: UNSPECIFIED in type: ${sortType.type}")
        }

        var res = when(sortType.type){
            SortTypesEnum.ALPHABETICAL -> array.sortedBy {
                it.displayableText.lowercase()
            }
            SortTypesEnum.WIN_RATE -> array.sortedBy {
                it.winRate()
            }

            else -> throw IllegalArgumentException("Cannot sort this type: ${sortType.type.name}")
        }

        if (sortType.mode == SortTypeMode.REVERSED){
            res = res.reversed()
        }
        return res
    }

}