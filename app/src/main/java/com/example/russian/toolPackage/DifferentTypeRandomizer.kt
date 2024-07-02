package com.example.russian.toolPackage

import com.example.russian.MyEnumClasses.SortTypesEnum
import com.example.russian.database.Word

class DifferentTypeRandomizer(
    val sortTypesEnum: SortTypesEnum,
    val array: List<Word>
) {

    fun random(): Word {
        return when(sortTypesEnum){
            SortTypesEnum.RANDOM -> simpleRandom()
            SortTypesEnum.RANDOM_WEIGHTED -> wightedRandom()
            SortTypesEnum.ALPHABETICAL -> alphabeticalOrder()
            else -> throw IllegalArgumentException("Cannot randomize by this type: ${sortTypesEnum.name}")
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