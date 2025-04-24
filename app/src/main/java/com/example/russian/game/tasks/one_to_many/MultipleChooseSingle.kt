package com.example.russian.game.tasks.one_to_many

import com.example.russian.game.tasks.TaskInterface

class MultipleChooseSingle(
    formatedString: String,
    maxOptions: Int = 3
) : TaskInterface {

    private val shuffledTasks: List<NarechiaOption>
    private val contextText: String

    init {
        val parts = formatedString.split(";")

        shuffledTasks = parts.subList(0, (parts.lastIndex - 1).coerceAtMost(maxOptions))
            .map {
                NarechiaOption(
                    text = it.replace("*", ""),
                    correct = it.contains("*")
                )
            }

        contextText = parts.last()
    }

    override fun isCorrect(answerInt: Int): Boolean {
        return shuffledTasks[answerInt].correct
    }

    override fun isCorrect(answerString: String): Boolean {
        shuffledTasks.forEach { value ->
            if (value.correct && value.text == answerString) return true
        }
        return false
    }

    override fun getTaskText(): String {
        return contextText.split("|").random()
    }

    override fun getPosibleVariants(): List<String> {
        return shuffledTasks.map {
            it.text
        }
    }

    override fun getCorrectAnswer(): Int {
        shuffledTasks.forEachIndexed { index, value ->
            if (value.correct) return index
        }
        return -1
    }


}

private data class NarechiaOption(
    val text: String,
    val correct: Boolean
)