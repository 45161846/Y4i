package com.example.russian.enums

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
