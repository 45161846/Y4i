package com.example.russian.main.tool

import com.example.russian.main.enums.MyTimerMode
import com.example.russian.main.enums.SortTypesEnum

data class GameSettings(
    val amountOfWordsInGame: Int,
    val timerMode: MyTimerMode,
    val timerLimit: Int,
    val sortedBy: SortTypesEnum,
    val delayBetweenAnswerAndNextTask: Long
)
