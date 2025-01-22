package com.example.russian.game.tool

import com.example.russian.main.stats.comp.filter.SortType
import com.example.russian.game.enums.MyTimerMode


//TODO
// add different game modes
interface GameSettings{
    val amountOfWordsInGame: Int
    val timerMode: MyTimerMode
    val timerLimit: Int
    val sortedBy: SortType
    val delayBetweenAnswerAndNextTask: Long
}
