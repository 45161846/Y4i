package com.example.russian.main

import com.example.russian.R
import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class Id(
    val value: Long
)

typealias Counter = Int

@JvmInline
value class Time(
    val value: Long = System.currentTimeMillis()
)

enum class TaskType{

    NARECHIA, PARONIM, YDARENIA, CLICKABLE

}

fun TaskType.iconId() = when(this){
    TaskType.NARECHIA -> R.drawable.ic_launcher_foreground
    TaskType.PARONIM -> R.drawable.paromins_icon
    TaskType.YDARENIA -> R.drawable.ydar_icon
    TaskType.CLICKABLE -> R.drawable.ic_launcher_foreground
}

enum class TaskThemes{
    YDAR, PARONIMS, SEPARATE, DOUBLE_N, COMMA, PASTE_LETTER, PREFIX, POSTFIX, VERB_POSTFIX
}