package com.example.russian.MyEnumClasses

import kotlinx.serialization.Serializable



open class MyFilterSettings(
    open var topics: Array<Boolean> = Array(TaskTopic().getTopicIntToNameMap().size){ true },
    var typeOfSort: SortType = SortType.ALPHABETICAL,
    var showUnanswered: Boolean,
    var prefix: String
){

    fun addTopic(i: Int){
        topics
    }
}

fun defaultFilterSettings() = MyFilterSettings(
    typeOfSort = SortType.ALPHABETICAL,
    showUnanswered = true,
    prefix = String()
)



@Serializable
object ScreenFilters

@Serializable
object ScreenStats