package com.example.russian.gameClasses.viewmodel.hood

interface HoodStateInterface {

    fun updateTimer(newTime: Long)

    fun correct()

    fun incorrect()

    fun correctCounter(): Int

    fun incorrectCounter(): Int
}