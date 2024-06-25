package com.example.russian.MyEnumClasses

class TaskTopic{

    val NARECHI9 = 0
    val PARONIM = 1
    val YDARENI9 = 2

    fun getTopicIntToNameMap() = mapOf(
        Pair(0, "Наречия"),
        Pair(1, "Паронимы"),
        Pair(2, "Ударения")
    )

}