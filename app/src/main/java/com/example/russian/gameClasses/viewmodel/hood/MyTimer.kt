package com.example.russian.gameClasses.viewmodel.hood

class MyTimer(override var time: Long): MyTimerInterface {
    override fun updateTime(newTime: Long) {
        time = newTime
    }
}