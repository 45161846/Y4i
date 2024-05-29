package com.example.russian.toolPackage

import android.util.Log
import com.example.russian.MyEnumClasses.Letters
import com.example.russian.MyEnumClasses.TaskTopic
import com.example.russian.MyTaskNarechia
import com.example.russian.database.Word
import com.example.russian.gameClasses.ydareni9.Ydareni9Task
import kotlin.random.Random

class WordToTaskMapper {

    fun wordToNarechieTask(w: Word): MyTaskNarechia {
        return MyTaskNarechia(id = w.id, data = w.value)
    }

    fun toContextTask(input: String): TaskInterface{
        return if(Random.nextBoolean()){
            inputToContextParonimTask(input)
        }else{
            inputToParonimContextTask(input)
        }
    }
    private fun inputToParonimContextTask(input: String): ByContextTaskInterface{

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

    private fun inputToContextParonimTask(input: String): ContextByTaskInterface{
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

    private fun splitIntoSingleParonims(input: String): List<String>{
        return input.trim().lowercase().split(" - ")
    }

    private fun createParonimsList(l: List<String>): ParonimSequence{
        val res = List(l.size){
            val s = l[it].split(" ")
            Paronim(s[0], s[1])
        }
        return ParonimSequence(res)
    }

    fun getAllParonimsNoCotext(input: String): List<String>{
        return createParonimsList(splitIntoSingleParonims(input)).getAllparonims()
    }

    fun inputToYdareni9Task(correctAnswer: String): Ydareni9Task{
        val glas = arrayOf("А", "О", "У", "И", "Е", "Я", "Ю", "Ё", "Ы", "Э")
        var correctAnswerInd = -1
        val listOfLetters = List(correctAnswer.length){
            val c = correctAnswer[it]
            val type: Letters
            if(c.isUpperCase())
            {
                type = Letters.YDARNA9
                correctAnswerInd = it
            }else if(c.uppercase() in glas){
                    type = Letters.BESYDARNA9
            }else{
                    type = Letters.SOGLASNA9
            }
            SingleLetter(
                c.lowercase(),
                type
            )
        }
        return Ydareni9Task(
            correctAnswer.lowercase(),
            listOfLetters,
            correctAnswerInd
        )
    }

    fun getDisplayableText(w: Word): String{
        Log.d("myTag_mapper","Starting: ${w.value}")
        return when(w.topic) {
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

    fun getDisplayableText(w: List<Word>): List<String>{
        return List(w.size){
            getDisplayableText(w[it])
        }
    }

}