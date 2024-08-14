package com.example.russian.gameClasses.viewmodel

import com.example.russian.architecture.data.olddata.entity.WordWithSpellings

interface Answer {

    fun isCorrect(wordWithSpelling: WordWithSpellings): Boolean

}