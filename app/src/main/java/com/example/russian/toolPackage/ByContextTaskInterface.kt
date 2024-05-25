package com.example.russian.toolPackage

import kotlin.math.min

class ByContextTaskInterface(
    val paronim: String,
    var contexts: List<String>,
    var correctAnswerIndex: Int
): TaskInterface {

    override fun isCorrect(answerInt: Int): Boolean{
        return answerInt == correctAnswerIndex
    }

    override fun isCorrect(answerString: String): Boolean{
        return answerString == contexts[correctAnswerIndex]
    }

    init{
        val ans = contexts[correctAnswerIndex]
        contexts = contexts
            .minus(ans)
            .shuffled()
            .subList(0,min(1, contexts.size - 1))
            .plus(ans)
            .shuffled()
        correctAnswerIndex = contexts.indexOf(ans)
    }

    override fun getTaskText(): String{
        return paronim
    }

    override fun getPosibleVariants(): List<String> {
        return contexts
    }

    override fun getCorrectAnswer(): Int {
        return correctAnswerIndex
    }

}