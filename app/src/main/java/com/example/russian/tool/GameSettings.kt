package com.example.russian.tool

import com.example.russian.enums.MyTimerMode
import com.example.russian.enums.SortTypesEnum

data class GameSettings(
    val amountOfWordsInGame: Int,
    val timerMode: MyTimerMode,
    val timerLimit: Int,
    val sortedBy: SortTypesEnum,
    val delayBetweenAnswerAndNextTask: Long
)
