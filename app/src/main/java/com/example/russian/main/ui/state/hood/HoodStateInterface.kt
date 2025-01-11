package com.example.russian.main.ui.state.hood

interface HoodStateInterface {

    fun updateTimer(newTime: Long)

    fun correct()

    fun incorrect()

    fun correctCounter(): Int

    fun incorrectCounter(): Int
}