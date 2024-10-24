package com.example.russian.enums

class TaskTopic{

    val NARECHI9: TaskTopicType = 0
    val PARONIM: TaskTopicType = 1
    val YDARENI9: TaskTopicType = 2
    val CLICKABLE: TaskTopicType = 3

    fun getTopicIntToNameMap() = mapOf(
        Pair(0, "Наречия"),
        Pair(1, "Паронимы"),
        Pair(2, "Ударения")
    )

}

typealias TaskTopicType = Int