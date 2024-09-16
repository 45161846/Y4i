package com.example.russian.enums

import kotlinx.serialization.Serializable


open class MyFilterSettings(
    override var topics: Array<Boolean> = Array(TaskTopic().getTopicIntToNameMap().size){ true },
    override var sortVariants: Array<SortType> = defaultSortType(),
    override var showUnanswered: Boolean,
    override var prefix: String,
): MyFilterSettingsArch{

    override fun changeUnanswered(): Boolean{
        showUnanswered = !showUnanswered
        return showUnanswered
    }

    override fun changeSortVariant(clickedIndex: Int){
        sortVariants[clickedIndex].changeAfterClick()
    }

    override fun changeTopicState(clickedTopicIndex: Int){
        topics[clickedTopicIndex] = topics[clickedTopicIndex].not()
    }

    override fun changePrefix(pref: String) {
        prefix  = pref
    }

    override fun copy(filterSettings: MyFilterSettingsArch) {

        this.prefix = filterSettings.prefix
        this.topics = filterSettings.topics
        this.sortVariants = filterSettings.sortVariants
        this.showUnanswered = filterSettings.showUnanswered

    }

    override fun defaultFilterSettings(resetPrefix: Boolean) = MyFilterSettings(
        prefix = if(resetPrefix) "" else this.prefix,
        topics = Array(TaskTopic().getTopicIntToNameMap().size){ true },
        sortVariants = defaultSortType(),
        showUnanswered = true
    )

    companion object Default{
        fun filterSettings() = MyFilterSettings(
        sortVariants = defaultSortType(),
        showUnanswered = true,
        prefix = String()
        )
    }
}

@Serializable
object ScreenFilters

@Serializable
object ScreenStats