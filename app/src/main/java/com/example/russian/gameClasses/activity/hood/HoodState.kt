package com.example.russian.gameClasses.activity.hood

class HoodState: HoodStateInterface {

    private var correct: Int = 0
    private var incorrect: Int = 0

    private val timer: MyTimerInterface = MyTimer(defaultTime())

    override fun updateTimer(newTime: Long) {
        timer.updateTime(newTime)
    }

    override fun correct() {
        correct++
    }

    override fun incorrect() {
        incorrect++
    }

    override fun correctCounter() = correct

    override fun incorrectCounter() = incorrect
}

fun defaultTime() = 5000L