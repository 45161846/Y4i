package com.example.russian.main.tool.random

interface RandomStorage<T>{

    fun savedRandom(): List<T>

    fun shuffle()

    fun shuffled(): List<T>

    fun setNewValues(items: Collection<T>, detector: CorrectDetector<T>)
}