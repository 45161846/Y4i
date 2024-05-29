package com.example.russian.toolPackage

import kotlin.math.min

class ContextByTaskInterface(
    val context: String,
var paronims: List<String>,
var correctAnswerIndex: Int
): TaskInterface
{
    override fun isCorrect(answerInt: Int): Boolean {
        return correctAnswerIndex == answerInt
    }

    override fun isCorrect(answerString: String): Boolean {
        return paronims[correctAnswerIndex] == answerString
    }

    override fun getTaskText(): String {
        return context
    }

    override fun getPosibleVariants(): List<String> {
        return paronims
    }

    override fun getCorrectAnswer(): Int {
        return correctAnswerIndex
    }

    init {
        val ans = paronims[correctAnswerIndex]
        paronims = paronims
            .minus(ans)
            .shuffled()
            .subList(0, min(2, paronims.size - 1))
            .plus(ans)
            .shuffled()
        correctAnswerIndex = paronims.indexOf(ans)
    }


}