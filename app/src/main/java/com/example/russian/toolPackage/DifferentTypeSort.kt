package com.example.russian.toolPackage

import com.example.russian.MyEnumClasses.SortType
import com.example.russian.MyEnumClasses.SortTypeMode
import com.example.russian.MyEnumClasses.SortTypesEnum
import com.example.russian.architecture.data.olddata.entity.Word

class DifferentTypeSort(
    val sortType: SortType,
    val array: List<Word>
) {

    fun sort(): List<Word>{

        if(sortType.mode == SortTypeMode.UNSPECIFIED){
            throw IllegalArgumentException("Illegal mode of sort: UNSPECIFIED in type: ${sortType.type}")
        }

        var res = when(sortType.type){
            SortTypesEnum.ALPHABETICAL -> array.sortedBy {
                it.displayableText
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