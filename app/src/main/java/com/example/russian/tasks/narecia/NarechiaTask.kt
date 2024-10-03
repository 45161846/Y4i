package com.example.russian.tasks.narecia

import com.example.russian.back.data.entity.Spelling
import com.example.russian.tasks.TaskInterface
import kotlin.random.Random

class NarechiaTask(): TaskInterface {

    val maxLength = 3

    private lateinit var shuffledSpellings: List<Spelling>
    private lateinit var contextText: String


    override fun isCorrect(answerInt: Int): Boolean {
        return shuffledSpellings[answerInt].isCorrect
    }

    override fun isCorrect(answerString: String): Boolean {
        shuffledSpellings.forEach{value ->
            if (value.isCorrect && value.value == answerString) return true
        }
        return false
    }

    override fun getTaskText(): String {
        return contextText.split("|").random()
    }

    override fun getPosibleVariants(): List<String> {
        return List(shuffledSpellings.size){
            shuffledSpellings[it].value
        }
    }

    override fun getCorrectAnswer(): Int {
        shuffledSpellings.forEachIndexed { index, value ->
            if (value.isCorrect) return index
        }
        return  -1
    }

    constructor(
        spellings: List<Spelling>,
        contextText: String
    ) : this(){
        shuffledSpellings = spellings.shuffled()
        this.contextText = contextText
    }

}