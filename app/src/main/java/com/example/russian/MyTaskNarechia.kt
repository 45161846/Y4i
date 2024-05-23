package com.example.russian

import com.example.russian.database.Word
import kotlin.math.max

class MyTaskNarechia(id: Int = 0, data: String, delimiterChar: Char = '_', correct: Int = 0, attempt: Int = 0) {
    
    private val delimiters = listOf("", "-", " ", "_")
    private val charDelimiters = listOf('-', ' ', '_', '*')

    var options: List<String?> = emptyList()
    var correctAnswer = -1
    var contextText = ""

    val task_id = id

    val correctAnswersAmount = correct
    val attemptAmount = attempt
    val percentage = (correct.toFloat() / attempt.toFloat())

    constructor(
        word: Word
    ) : this(
        id = word.id,
        data = word.value,
        correct = word.gotItRight,
        attempt = word.attempts
    )

    init {

        val params = data.split(";")

        when(params.size){
            1 -> {
                initWithOne(params[0])
            }
            else -> {
                initWithContext(params)
            }
        }
        contextText = contextText.replace("\r", "")

        }

    private fun initWithOne(str: String){
        val parts = str.split("*", "-", " ")
        for (opt: String in delimiters) {
            options = options.plus(parts.joinToString(separator = opt))
        }
        correctAnswer = if(" " in str){
            2
        }else if("-" in str){
            1
        }else{
            0
        }
    }
    
    private fun initWithAnswers(params: List<String>){
        
        val opt = params[1].split("|")
        
        if (opt.size < 2){
            initWithOne(params[0])
            return
        }

        generateAllVariants(params[0], opt)

    }
    
    private fun initWithContext(params: List<String>){
        
        initWithAnswers(params)
        contextText = if( "|" !in params.last()){
            params.last()
        }else{
            getOneContextWord(params.last())
        }
        
    }

    private fun getOneContextWord(words: String):String{
        return words.split("|").random()
    }
    
    private fun generateAllVariants(word: String, variants: List<String>){
        
        val possibleWords = arrayOfNulls<String>(variants.size)
        
        for(i in variants.indices){
            val v = variants[i]
            possibleWords[i] = putDelimitersInWord(word, v)
        }

        options = possibleWords.toList()
        leftThreeOrLessOptions()
        
    }
    
    private fun putDelimitersInWord(word: String, delimiters: String): String{
        
        val chars = word.toCharArray()
        
        var index = 0
        for(i in chars.indices){
            val c = chars[i]
            if(c in charDelimiters){
                chars[i] = delimiters[index % max(1, delimiters.length)]
                index++
            }
        }

        return String(chars).replace("*", "").lowercase()
    }

    private fun leftThreeOrLessOptions(){
        val correctAnswerText = options[0]

        if(options.size > 3){

            val first = (1 until options.size).random()
            var second = (1 until options.size - 1).random()

            if(second >= first){
                second++
            }
            options = listOf(
                correctAnswerText,
                options[first],
                options[second]
            )
        }

        options = options.shuffled()
        correctAnswer = options.indexOf(correctAnswerText)

    }


    
}