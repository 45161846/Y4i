package com.example.russian.architecture.data.olddata.entity

class WordOperations {

    companion object : WordOperationsInterface {
        override fun wordType(word: Word): WordType {
            return when(word.topic){
                0 -> WordType.NARECIA
                1 -> WordType.PARONIM
                2 -> WordType.PARONIM
                else -> WordType.ELSE
            }
        }
    }

}

enum class WordType{
    NARECIA, PARONIM, YDARENIA, ELSE
}

interface WordOperationsInterface{
    fun wordType(word: Word) : WordType
}