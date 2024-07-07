package com.example.russian.MyEnumClasses

class MyFilterSettingsImpl(
    override var topics: Array<Boolean>,
    override var sortVariants: Array<SortType>,
    override var showUnanswered: Boolean,
    override var prefix: String,

):MyFilterSettingsArch{
    override fun changeUnanswered(): Boolean {
        showUnanswered = !showUnanswered
        return showUnanswered
    }

    override fun changeSortVariant(clickedIndex: Int) {
        sortVariants[clickedIndex].changeAfterClick()
    }

    override fun changeTopicState(clickedTopicIndex: Int) {
        topics[clickedTopicIndex] = topics[clickedTopicIndex].not()
    }

    override fun changePrefix(pref: String) {
        prefix = pref
    }

    override fun copy(filterSettings: MyFilterSettingsArch) {

        this.prefix = filterSettings.prefix
        this.topics = filterSettings.topics
        this.sortVariants = filterSettings.sortVariants
        this.showUnanswered = filterSettings.showUnanswered

    }

    override fun defaultFilterSettings(resetPrefix: Boolean) = MyFilterSettingsImpl(
        prefix = if(resetPrefix) "" else this.prefix,
        topics = Array(TaskTopic().getTopicIntToNameMap().size){ true },
        sortVariants = defaultSortType(),
        showUnanswered = true
    )

    companion object Default{
        fun defaultFilterSettings() = MyFilterSettingsImpl(
            topics = Array(TaskTopic().getTopicIntToNameMap().size){ true },
            sortVariants = defaultSortType(),
            showUnanswered = true,
            prefix = String())
    }

}