package com.example.russian.MyEnumClasses

import kotlinx.serialization.Serializable


@Serializable
data class MyFilterSettings(
    val topics: Set<Int>,
    val typeOfSort: SortType,
    val showUnanswered: Boolean,
    val prefix: String
)

fun defaultFilterSettings() = MyFilterSettings(
    TaskTopic().getTopicIntToNameMap().keys,
    typeOfSort = SortType.ALPHABETICAL,
    showUnanswered = true,
    prefix = String()
)

@Serializable
object ScreenFilters

@Serializable
object ScreenStats