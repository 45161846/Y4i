package com.example.russian.tasks.paronim

import com.example.russian.architecture.data.olddata.entity.Spelling
import com.example.russian.architecture.data.olddata.entity.WordWithSpellings
import com.example.russian.tasks.TaskInterface
import kotlin.math.min
import kotlin.random.Random

class ParonimTask(
    spellings: List<Spelling>,
): TaskInterface{

    private val shuffledParonims: ShuffledParonims

    private val mode = if(Random.nextBoolean()) ParonimMode.BY_PARONIM else ParonimMode.BY_CONTEXT

    private val displayedTask: String
    private val correctAnswerIndex: Int

    private val possibleDisplayedOptions: List<String>
    private val correctString: String

    init {
        val paronims = spellingsToParonimsList(spellings)

        val preShuffled = List(paronims.size){
            PreShuffledParonim(
                paronim = paronims[it],
                index = it
            )
        }

        shuffledParonims = ShuffledParonims(preShuffled)

        val shuffled = shuffledParonims.shuffledParonims

        when(mode){
            ParonimMode.BY_PARONIM -> {
                displayedTask = shuffledParonims.correctParonim.paronimValue
                correctString = shuffledParonims.correctParonim.context
                possibleDisplayedOptions = List(shuffled.size){
                    shuffled[it].context
                }
            }
            ParonimMode.BY_CONTEXT -> {
                displayedTask = shuffledParonims.correctParonim.context
                correctString = shuffledParonims.correctParonim.paronimValue
                possibleDisplayedOptions = List(shuffled.size){
                    shuffled[it].paronimValue
                }
            }
        }
        correctAnswerIndex = shuffledParonims.correctParonim.index
    }

    override fun isCorrect(answerInt: Int): Boolean {
        return correctAnswerIndex == answerInt
    }

    override fun isCorrect(answerString: String): Boolean {
        return correctString == answerString
    }

    override fun getTaskText(): String {
        return displayedTask
    }

    override fun getPosibleVariants(): List<String> {
        return possibleDisplayedOptions
    }

    override fun getCorrectAnswer(): Int {
        return correctAnswerIndex
    }

}

fun spellingsToParonimsList(spellings: List<Spelling>): List<Paronim>{
    return List(spellings.size){
        val splited = spellings[it].value.split(" ")
        Paronim(
            paronim = splited[0],
            context = splited[1].replace("_", " ")
        )
    }
}

private class PreShuffledParonim(
    paronim: Paronim,
    val index: Int
){
    val context: String = paronim.context
    val paronimValue: String = paronim.paronim
}

private class ShuffledParonims(
    paronims: List<PreShuffledParonim>
){
    val correctParonim: PreShuffledParonim = paronims.random()

    val shuffledParonims = paronims
        .filter {
            it.paronimValue != correctParonim.paronimValue
        }
        .shuffled()
        .slice(0..min(paronims.lastIndex - 1, 1))
        .plus(correctParonim)
        .shuffled()
}

private enum class ParonimMode{
    BY_CONTEXT,
    BY_PARONIM
}