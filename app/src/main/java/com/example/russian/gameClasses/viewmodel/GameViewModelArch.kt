package com.example.russian.gameClasses.viewmodel

import com.example.russian.architecture.data.entity.WordWithSpellings
import com.example.russian.gameClasses.viewmodel.hood.HoodStateInterface

interface GameViewModelArch {

    fun currentWord(): WordWithSpellings

    suspend fun correct(answeredIndex: Int)

    suspend fun incorrect(answeredIndex: Int)

    fun getHood(): HoodStateInterface
}