package com.example.russian.tasks.paronim

import com.example.russian.back.data.entity.Spelling
import com.example.russian.tasks.TaskInterface
import kotlin.random.Random

class ParonimTask(
    spellings: List<Spelling>
): TaskInterface {

    private val mode = if(Random.nextBoolean()) ParonimMode.BY_PARONIM else ParonimMode.BY_CONTEXT

    private val paronims: List<ParonimType>

    private val correctParonim: Paronim

    private val correctIndex: Int

    init {
        val allParonims = spellingsToParonimsList(spellings)

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

    private fun spellingsToParonimsList(spellings: List<Spelling>): List<Paronim>{
        return List(spellings.size){
            val splited = spellings[it].value.split(" ")
            Paronim(
                paronim = splited[0],
                context = splited[1].replace("_", " ")
            )
        }
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