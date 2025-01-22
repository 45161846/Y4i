package com.example.russian.game.tasks.narecia

import com.example.russian.game.back.data.entity.PartOfTask
import com.example.russian.game.tasks.TaskInterface

class NarechiaTask(): TaskInterface {

    val maxLength = 3

    private lateinit var shuffledPartOfTasks: List<PartOfTask>
    private lateinit var contextText: String


    override fun isCorrect(answerInt: Int): Boolean {
        return shuffledPartOfTasks[answerInt].isCorrect
    }

    override fun isCorrect(answerString: String): Boolean {
        shuffledPartOfTasks.forEach{value ->
            if (value.isCorrect && value.value == answerString) return true
        }
        return false
    }

    override fun getTaskText(): String {
        return contextText.split("|").random()
    }

    override fun getPosibleVariants(): List<String> {
        return List(shuffledPartOfTasks.size){
            shuffledPartOfTasks[it].value
        }
    }

    override fun getCorrectAnswer(): Int {
        shuffledPartOfTasks.forEachIndexed { index, value ->
            if (value.isCorrect) return index
        }
        return  -1
    }

    constructor(
        PartOfTasks: List<PartOfTask>,
        contextText: String
    ) : this(){
        shuffledPartOfTasks = PartOfTasks.shuffled()
        this.contextText = contextText
    }

}