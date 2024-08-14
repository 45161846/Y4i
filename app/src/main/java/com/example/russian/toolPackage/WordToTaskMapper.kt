package com.example.russian.toolPackage

import com.example.russian.MyEnumClasses.Letters
import com.example.russian.MyEnumClasses.TaskTopic
import com.example.russian.MyEnumClasses.TaskTopicEnum
import com.example.russian.architecture.data.olddata.entity.Spelling
import com.example.russian.tasks.narecia.MyTaskNarechia
import com.example.russian.architecture.data.olddata.entity.Word
import com.example.russian.architecture.data.olddata.entity.WordWithSpellings
import com.example.russian.gameClasses.viewmodel.randomaizer.RandomStorage
import com.example.russian.tasks.ydarenia.Ydareni9Task
import com.example.russian.tasks.paronim.ByContextTaskInterface
import com.example.russian.tasks.paronim.ContextByTaskInterface
import com.example.russian.tasks.paronim.Paronim
import com.example.russian.tasks.paronim.ParonimSequence
import com.example.russian.tasks.ydarenia.SingleLetter
import com.example.russian.tasks.TaskInterface
import com.example.russian.tasks.narecia.NarechiaTask
import com.example.russian.tasks.paronim.ParonimTask
import kotlin.random.Random

class WordToTaskMapper {

    companion object {

        fun wordToTask(w: Word): TaskInterface {
            return when (w.topic) {
                TaskTopicEnum.NARECHIA -> wordToNarechieTask(w)
                TaskTopicEnum.YDARENIA -> inputToYdareni9Task(w.value)
                TaskTopicEnum.PARONIM -> toContextTask(w.value)
                else -> {
                    throw Error("Unexpected topic of word")
                }
            }
        }
        fun wordToNarechieTask(w: Word): MyTaskNarechia {
            return MyTaskNarechia(id = w.id, data = w.value)
        }

        fun toContextTask(input: String): TaskInterface {
            return if (Random.nextBoolean()) {
                inputToContextParonimTask(input)
            } else {
                inputToParonimContextTask(input)
            }
        }

        private fun inputToParonimContextTask(input: String): ByContextTaskInterface {

            val options = createParonimsList(splitIntoSingleParonims(input))
            val correctPair = options.similar.random()
            val correctContext = correctPair.context
            val allContexts = options.getAllcontexts()
            val ind = allContexts.indexOf(correctContext)

            return ByContextTaskInterface(
                correctPair.paronim,
                allContexts,
                ind
            )

        }

        private fun inputToContextParonimTask(input: String): ContextByTaskInterface {
            val options = createParonimsList(splitIntoSingleParonims(input))
            val correctPair = options.similar.random()
            val correctParonim = correctPair.paronim
            val allParonims = options.getAllparonims()
            val ind = allParonims.indexOf(correctParonim)

            return ContextByTaskInterface(
                correctPair.context,
                allParonims,
                ind
            )
        }

        private fun splitIntoSingleParonims(input: String): List<String> {
            return input.trim().lowercase().split(" - ")
        }

        private fun createParonimsList(l: List<String>): ParonimSequence {
            val res = List(l.size) {
                val s = l[it].split(" ")
                Paronim(s[0], s[1])
            }
            return ParonimSequence(res)
        }

        fun getAllParonimsNoCotext(input: String): List<String> {
            return createParonimsList(splitIntoSingleParonims(input)).getAllparonims()
        }

        fun inputToYdareni9Task(correctAnswer: String): Ydareni9Task {
            val glas = arrayOf("А", "О", "У", "И", "Е", "Я", "Ю", "Ё", "Ы", "Э")
            var correctAnswerInd = -1
            val listOfLetters = List(correctAnswer.length) {
                val c = correctAnswer[it]
                val type: Letters
                if (c.isUpperCase()) {
                    type = Letters.YDARNA9
                    correctAnswerInd = it
                } else if (c.uppercase() in glas) {
                    type = Letters.BESYDARNA9
                } else {
                    type = Letters.SOGLASNA9
                }
                SingleLetter(
                    c.lowercase(),
                    type
                )
            }
            return Ydareni9Task(
                correctAnswer.lowercase()
            )
        }

        fun getDisplayableText(w: Word): String {
            return when (w.topic) {
                TaskTopic().NARECHI9 -> {
                    val narechie = wordToNarechieTask(w)
                    narechie.options[narechie.correctAnswerIndex]
                }

                TaskTopic().PARONIM -> {
                    val paronim = getAllParonimsNoCotext(w.value)
                    paronim.joinToString(" - ")
                }

                TaskTopic().YDARENI9 -> {
                    w.value
                }

                else -> {
                    "Unknown word"
                }

            }
        }

        fun getDisplayableText(w: List<Word>): List<String> {
            return List(w.size) {
                getDisplayableText(w[it])
            }
        }

        fun getTaskContextWord(inputValue: String): String{
            val parts = inputValue.split(";")

            if (parts.size < 2) return ""

            return parts.last()
        }

        fun getSpellingVariants(inputValue: String): List<String>{
            return listOf()
        }

        fun wordWithSpellingToTask(wordWithSpelling: WordWithSpellings): TaskInterface {
            return when(wordWithSpelling.word.topic){
                TaskTopicEnum.NARECHIA -> wordWithSpellingToNarechiaTask(wordWithSpelling)
                TaskTopicEnum.PARONIM -> wordWithSpellingToParonimTask(wordWithSpelling)
                TaskTopicEnum.YDARENIA -> wordWithSpellingToYdareniaTask(wordWithSpelling.word.value)
                else -> {
                    throw IllegalArgumentException("Cannot create task of topic: ${wordWithSpelling.word.topic}")
                }
            }
        }

        private fun wordWithSpellingToNarechiaTask(wordWithSpelling: WordWithSpellings): NarechiaTask {
            return NarechiaTask(wordWithSpelling)
        }

        private fun wordWithSpellingToParonimTask(wordWithSpelling: WordWithSpellings): ParonimTask{
            return ParonimTask(wordWithSpelling.spellings)
        }

        private fun wordWithSpellingToYdareniaTask(inputValue: String): Ydareni9Task{

            var correctAnswer: Int? = null

            val letters = List(inputValue.length){

                val letter = inputValue[it].toString()

                if(letter == letter.uppercase()) correctAnswer = it

                SingleLetter(
                    letter,
                    SingleLetter.letterType(letter)
                )
            }

            return Ydareni9Task(inputValue)
        }

        fun wordWithSpellingToTask(word: Word, randomStorage: RandomStorage<Spelling>): TaskInterface {

            return when(word.topic){
                TaskTopicEnum.NARECHIA -> NarechiaTask(randomStorage.savedRandom(), word.context)
                TaskTopicEnum.PARONIM -> ParonimTask(randomStorage.savedRandom())
                TaskTopicEnum.YDARENIA -> wordWithSpellingToYdareniaTask(word.value)
                else -> {
                    throw IllegalArgumentException("Cannot create task of topic: ${word.topic}")
                }
            }
        }
    }
}