package com.example.russian.main.tool.random

interface CorrectDetector<T>{
    fun isCorrect(obj: T): Boolean
}