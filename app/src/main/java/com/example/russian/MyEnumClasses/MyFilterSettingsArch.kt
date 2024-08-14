package com.example.russian.MyEnumClasses

import com.example.russian.architecture.data.olddata.entity.Word
import com.example.russian.toolPackage.DifferentTypeSort
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

interface MyFilterSettingsArch {

    var topics: Array<Boolean>
    var sortVariants: Array<SortType>
    var showUnanswered: Boolean
    var prefix: String

    fun changeUnanswered(): Boolean

    fun changeSortVariant(clickedIndex: Int)

    fun changeTopicState(clickedTopicIndex: Int)

    fun changePrefix(pref: String)

    fun copy(filterSettings: MyFilterSettingsArch)

    fun defaultFilterSettings(resetPrefix: Boolean = true): MyFilterSettingsArch
}

class FilterApplier{

    companion object{
        @OptIn(ExperimentalCoroutinesApi::class)
        fun applyOnFlow(unfilteredFlow: Flow<List<Word>>, filter: MyFilterSettingsArch): Flow<List<Word>> {
            return unfilteredFlow.mapLatest {unfilteredList ->
                val l = filterList(unfilteredList, filter)
                l
            }
        }

        suspend fun filterList(list: List<Word>, filter: MyFilterSettingsArch): List<Word>{

            val filteredList = list
                .filter {word ->
                    filter.topics[word.topic] && (filter.showUnanswered || word.attempts > 0)
                }
                .filter { word ->
                    word.displayableText.lowercase().startsWith(filter.prefix.lowercase())
                }
            var sortVariant = SortType(SortTypesEnum.ALPHABETICAL)
            filter.sortVariants.forEach { variant ->
                if(variant.mode != SortTypeMode.UNSPECIFIED){
                    sortVariant = variant
                }
            }

            return DifferentTypeSort(sortVariant, filteredList).sort()
        }
    }
}