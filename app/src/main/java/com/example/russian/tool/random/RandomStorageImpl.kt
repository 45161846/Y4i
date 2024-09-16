package com.example.russian.tool.random

interface CorrectDetector<T>{
    fun isCorrect(obj: T): Boolean
}