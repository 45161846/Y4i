package com.example.russian.toolPackage

import com.example.russian.MyEnumClasses.SortType
import com.example.russian.MyEnumClasses.SortTypeMode
import com.example.russian.MyEnumClasses.SortTypesEnum
import com.example.russian.database.Word

class DifferentTypeSort(
    val sortType: SortType,
    val array: List<Word>
) {

    fun sort(): List<Word>{
        var res = when(sortType.type){
            SortTypesEnum.ALPHABETICAL -> array.sortedBy {
                WordToTaskMapper().getDisplayableText(it)
            }
            SortTypesEnum.WIN_RATE -> array.sortedBy {
                it.percentage
            }

            else -> throw IllegalArgumentException("Cannot sort this type: ${sortType.type.name}")
        }

        if (sortType.mode == SortTypeMode.REVERSED){
            res = res.reversed()
        }
        return res
    }

}