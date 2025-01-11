package com.example.russian.architectured

@JvmInline
value class Id(
    val value: Long
)

typealias Counter = Int

@JvmInline
value class Time(
    val value: Long
)

enum class TaskType{

    YDAR, PARONIMS, SEPARATE, DOUBLE_N, COMMA, PASTE_LETTER, PREFIX, POSTFIX, VERB_POSTFIX

}