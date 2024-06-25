package com.example.russian.toolPackage

import com.example.russian.MyEnumClasses.SortType
import com.example.russian.database.Word

class DifferentTypeSort(
    val sortType: SortType,
    val array: List<Word>
) {

    fun sort(): List<Word>{
        return when(sortType){
            SortType.ALPHABETICAL -> array.sortedBy {
                WordToTaskMapper().getDisplayableText(it)
            }
            SortType.ALPHABETICAL_REVERSED -> array.sortedBy {
                WordToTaskMapper().getDisplayableText(it)
            }.reversed()
            SortType.WIN_RATE -> array.sortedBy {
                it.percentage
            }
            SortType.WIN_RATE_REVERSED -> array.sortedBy {
                it.percentage
            }.reversed()
            else -> throw IllegalArgumentException("Cannot sort this type: ${sortType.name}")
        }
    }

}