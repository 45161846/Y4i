package com.example.russian.tasks.ydarenia

import com.example.russian.tasks.TaskInterface

class Ydareni9Task(
    val value: String,
): TaskInterface {

    private val correctAnswerIndex: Int

    init {

        var index: Int? = null

        value.forEachIndexed{ind, l ->

            if(l.toString() == l.toString().uppercase()){

                if(index == null){
                    index = ind
                }else{
                    throw RuntimeException("Too many capital letters for word $value to have a correct answer")
                }
            }
        }

        correctAnswerIndex = index
            ?: throw RuntimeException("No capital letter for word $value to have a correct answer")
    }

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
        val list = value.split("")
        return list.subList(1, list.lastIndex)
    }

    override fun getCorrectAnswer(): Int {
        return correctAnswerIndex
    }
}