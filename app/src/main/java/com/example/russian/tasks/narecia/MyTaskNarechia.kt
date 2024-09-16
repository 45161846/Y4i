package com.example.russian.tasks.narecia

import com.example.russian.tasks.TaskInterface
import kotlin.math.max

class MyTaskNarechia(
    id: Int = 0,
    data: String,
    delimiterChar: Char = '_',
    correct: Int = 0,
    attempt: Int = 0) : TaskInterface
{
    
    private val delimiters = listOf("", "-", " ", "_")
    private val charDelimiters = listOf('-', ' ', '_', '*')

    var options: List<String> = emptyList()
    var correctAnswerIndex = -1
    var contextText = ""

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
        correctAnswerIndex = if(" " in str){
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
        
        options = List(variants.size){
            val v = variants[it]
            putDelimitersInWord(word, v)
        }

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
        correctAnswerIndex = options.indexOf(correctAnswerText)

    }

    override fun isCorrect(answerInt: Int): Boolean {
        return correctAnswerIndex == answerInt
    }

    override fun isCorrect(answerString: String): Boolean {
        return options[correctAnswerIndex] == answerString
    }

    override fun getTaskText(): String {
        return contextText
    }

    override fun getPosibleVariants(): List<String> {
        return options
    }

    override fun getCorrectAnswer(): Int {
        return correctAnswerIndex
    }


}