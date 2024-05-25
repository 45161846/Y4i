package com.example.russian.gameClasses.ydareni9

import com.example.russian.toolPackage.SingleLetter
import com.example.russian.toolPackage.TaskInterface

class Ydareni9Task(
    val value: String,
    val letters: List<SingleLetter>,
    val correctAnswerIndex: Int
):TaskInterface {
    override fun isCorrect(answerInt: Int): Boolean {
        return answerInt == correctAnswerIndex
    }

    override fun isCorrect(answerString: String): Boolean {
        return false
    }

    override fun getTaskText(): String {
        return value
    }

    override fun getPosibleVariants(): List<String> {
        return emptyList()
    }

    override fun getCorrectAnswer(): Int {
        return -1
    }
}