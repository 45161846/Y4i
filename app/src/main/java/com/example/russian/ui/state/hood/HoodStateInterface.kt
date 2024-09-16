package com.example.russian.ui.state.hood

interface HoodStateInterface {

    fun updateTimer(newTime: Long)

    fun correct()

    fun incorrect()

    fun correctCounter(): Int

    fun incorrectCounter(): Int
}