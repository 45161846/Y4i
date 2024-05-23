package com.example.russian.toolPackage

import com.example.russian.MyEnumClasses.SortType
import com.example.russian.Word

class DifferentTypeRandomizer(
    val sortType: SortType,
    val array: List<Word>
) {

    fun random(): Word {
        return when(sortType){
            SortType.RANDOM -> simpleRandom()
            SortType.RANDOM_WEIGHTED -> wightedRandom()
            SortType.ALPHABETICAL -> alphabeticalOrder()
        }
    }

    private fun alphabeticalOrder(): Word {
        TODO("Not yet implemented")
    }

    private fun wightedRandom(): Word {
        TODO("Not yet implemented")
    }

    private fun simpleRandom(): Word {
        return array.random()
    }

}