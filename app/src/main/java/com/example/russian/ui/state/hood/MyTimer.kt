package com.example.russian.ui.state.hood

class MyTimer(override var time: Long): MyTimerInterface {
    override fun updateTime(newTime: Long) {
        time = newTime
    }
}