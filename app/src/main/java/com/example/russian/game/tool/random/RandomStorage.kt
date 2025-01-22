package com.example.russian.game.tool.random

interface RandomStorage<T>{

    fun savedRandom(): List<T>

    fun shuffle()

    fun shuffled(): List<T>

    fun setNewValues(items: Collection<T>, detector: CorrectDetector<T>)
}