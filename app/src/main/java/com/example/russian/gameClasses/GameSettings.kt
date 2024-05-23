package com.example.russian.gameClasses

import com.example.russian.MyEnumClasses.MyTimerMode
import com.example.russian.MyEnumClasses.SortType

data class GameSettings(
    val amountOfWordsInGame: Int,
    val timerMode: MyTimerMode,
    val timerLimit: Int,
    val sortedBy: SortType,
    val delayBetweenAnswerAndNextTask: Long
)
