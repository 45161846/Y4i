package com.example.russian.toolPackage

interface TaskInterface {

    fun isCorrect(answerInt: Int): Boolean

    fun isCorrect(answerString: String): Boolean

    fun getTaskText(): String

    fun getPosibleVariants(): List<String>

    fun getCorrectAnswer(): Int

}