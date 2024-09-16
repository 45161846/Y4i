package com.example.russian.tasks.narecia

import com.example.russian.back.data.entity.Spelling
import com.example.russian.back.data.entity.WordWithSpellings
import com.example.russian.tasks.TaskInterface

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
        return contextText
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

    constructor(wordWithSpellings: WordWithSpellings) : this(){
        shuffledSpellings = (wordWithSpellings.spellings.subList(0,1) +
                wordWithSpellings.spellings.subList(1, wordWithSpellings.spellings.size))
            .shuffled()
            .subList(0, maxLength.coerceAtMost(wordWithSpellings.spellings.size))

    }
}