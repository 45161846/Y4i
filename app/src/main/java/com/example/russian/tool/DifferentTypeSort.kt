package com.example.russian.tool

import com.example.russian.enums.SortType
import com.example.russian.enums.SortTypeMode
import com.example.russian.enums.SortTypesEnum
import com.example.russian.back.data.entity.Statistics

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