package com.example.russian.gameClasses

import com.example.russian.MyEnumClasses.MyTimerMode
import com.example.russian.MyEnumClasses.SortTypesEnum

data class GameSettings(
    val amountOfWordsInGame: Int,
    val timerMode: MyTimerMode,
    val timerLimit: Int,
    val sortedBy: SortTypesEnum,
    val delayBetweenAnswerAndNextTask: Long
)
