package com.example.russian.game.tasks.clickable

import com.example.russian.game.back.data.entity.PartOfTaskWithSpellingVariants
import com.example.russian.game.back.data.entity.SpellingVariant
import com.example.russian.game.tasks.TaskInterface
import com.example.russian.game.ui.state.ClickableWord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class ClickableWordsInTextTask(
    formatedText: String
) : TaskInterface {

//    private val parts = initialParts
//        .map { part ->
//            if (part.spellings.size == 1) {
//                part.spellings.first().value.split("(?= )".toRegex()).map {
//                    PartOfTaskWithSpellingVariants(
//                        part.partOfTask,
//                        listOf(
//                            SpellingVariant(
//                                partOfTaskId = part.partOfTask.taskId,
//                                value = it,
//                                correct = true
//                            )
//                        )
//                    )
//                }
//            } else {
//                listOf(part.copy(spellings = part.spellings.shuffled()))
//            }
//        }
//        .flatten()
//        .mapIndexed { ind, it ->
//            it.partOfTask.index = ind
//            it
//        }
//
//    private val correctIndexes = parts.map {
//        var index: Int? = null
//        it.spellings.forEachIndexed { ind, spel ->
//            if (spel.correct) {
//                index = ind
//                return@forEachIndexed
//            }
//        }
//        index ?: 0
//    }
//
//    val words = parts.mapIndexed { ind, part ->
//        if (part.spellings.size == 1) {
//            ClickableWord.NoClick(part.spellings.first().value)
//        } else {
//            ClickableWord.Clickable(
//                textFlow = MutableStateFlow(part.spellings[0].value.replace("*", "")),
//                correct = correctIndexes[ind] == 0
//            )
//        }
//    }
//    private val chosenIndexes = parts.map {
//        0
//    }.toMutableList()

    val words: List<ClickableWord> = emptyList()

    override fun isCorrect(answerInt: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun isCorrect(answerString: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun getPosibleVariants(): List<String> {
        TODO("Not yet implemented")
    }

    override fun getCorrectAnswer(): Int {
        TODO("Not yet implemented")
    }

    override fun getTaskText(): String {
        return "Нажимайте на слово, чтобы менять ответ"
    }

    fun clicked(ind: Int) {
//        val word = words[ind]
//
//        chosenIndexes[ind]++
//        chosenIndexes[ind] %= parts[ind].spellings.size
//        if (word is ClickableWord.Clickable) {
//            word.textFlow.value =
//                parts[ind]
//                    .spellings[chosenIndexes[ind]]
//                    .value
//                    .replace("*", "")
//            word.correct = correctIndexes[ind] == chosenIndexes[ind]
//        }
//
    }

    fun answered(): Boolean {
//        val res = words.foldIndexed(true) { ind, prev, word ->
//            when (word) {
//                is ClickableWord.NoClick -> prev
//                is ClickableWord.Clickable -> {
//                    word.textFlow.update {
//                        parts[ind].spellings[correctIndexes[ind]].value
//                    }
//                    prev && chosenIndexes[ind] == correctIndexes[ind]
//                }
//            }
//        }
//        return res
        return TODO()
    }
}
