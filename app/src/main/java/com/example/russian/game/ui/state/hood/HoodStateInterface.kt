package com.example.russian.game.ui.state.hood

interface HoodStateInterface {

    fun updateTimer(newTime: Long)

    fun correct()

    fun incorrect()

    fun correctCounter(): Int

    fun incorrectCounter(): Int
}