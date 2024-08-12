package com.example.russian.gameClasses.viewmodel.randomaizer

interface RandomStorage<T>{

    fun savedRandom(): List<T>

    fun shuffle()

    fun shuffled(): List<T>

    fun setNewValues(items: Collection<T>, detector: CorrectDetector<T>)
}