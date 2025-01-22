package com.example.russian.game.tool.random

interface CorrectDetector<T>{
    fun isCorrect(obj: T): Boolean
}