package com.example.russian.game.tasks.many_to_many

import com.example.russian.game.tasks.TaskInterface
import kotlin.random.Random

class FindPairTask(
    formatedText: String
): TaskInterface {

    private val mode = if(Random.nextBoolean()) ParonimMode.BY_PARONIM else ParonimMode.BY_CONTEXT

    private val paronims: List<ParonimType>

    private val correctParonim: Paronim

    private val correctIndex: Int

    init {

        val allParonims = inputToParonimsList(formatedText)

        val maxSize = 3

        correctIndex = allParonims.indices.random()

        correctParonim = allParonims[correctIndex]

        val rest = allParonims
            .minus(correctParonim)
            .shuffled()

        val mappedRest = rest.map {
            ParonimType.Incorrect(it)
        }

        paronims = mappedRest
            .slice(0..(maxSize - 2).coerceAtMost(mappedRest.lastIndex))
            .plus(ParonimType.Correct(correctParonim))
            .shuffled()

    }

    override fun isCorrect(answerInt: Int): Boolean {
        return paronims[answerInt] is ParonimType.Correct
    }

    override fun isCorrect(answerString: String): Boolean {
        return answerString == when(mode){
            ParonimMode.BY_PARONIM -> correctParonim.paronim
            ParonimMode.BY_CONTEXT -> correctParonim.context
        }
    }

    override fun getTaskText(): String {
        return when(mode){
            ParonimMode.BY_PARONIM -> correctParonim.paronim.split("|").random()
            ParonimMode.BY_CONTEXT -> correctParonim.context.split("|").random()
        }
    }

    override fun getPosibleVariants(): List<String> {
        return when(mode){
            ParonimMode.BY_PARONIM -> List(paronims.size){
                paronims[it].paronim.context
            }
            ParonimMode.BY_CONTEXT -> List(paronims.size){
                paronims[it].paronim.paronim
            }
        }
    }

    override fun getCorrectAnswer(): Int = correctIndex

    private fun inputToParonimsList(formatedText: String): List<Paronim>{
        return TODO()
    }
}


private sealed class ParonimType(
    open val paronim: Paronim
){

    data class Correct(override val paronim: Paronim) : ParonimType(paronim)

    data class Incorrect(override val paronim: Paronim) : ParonimType(paronim)

}


private enum class ParonimMode{
    BY_CONTEXT,
    BY_PARONIM
}