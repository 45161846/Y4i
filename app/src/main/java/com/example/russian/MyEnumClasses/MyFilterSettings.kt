package com.example.russian.MyEnumClasses

import kotlinx.serialization.Serializable



open class MyFilterSettings(
    open var topics: Array<Boolean> = Array(TaskTopic().getTopicIntToNameMap().size){ true },
    var sortVariants: Array<SortType> = defaultSortType(),
    var showUnanswered: Boolean,
    var prefix: String
){

    fun addTopic(i: Int){
        topics
    }
}

fun defaultFilterSettings() = MyFilterSettings(
    sortVariants = defaultSortType(),
    showUnanswered = true,
    prefix = String()
)



@Serializable
object ScreenFilters

@Serializable
object ScreenStats