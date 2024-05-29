package com.example.russian

import com.example.russian.database.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class WordsLocalRepository(words: List<Word> = emptyList()) {

    var currentWords: ArrayList<Word> = ArrayList(words)

    suspend fun setWords(words: List<Word>): Boolean{
        return withContext(Dispatchers.Main){
            currentWords = ArrayList(words)
            true
        }
    }

    fun updateWord(w: Word){
        for(index in currentWords.indices){
            if(currentWords[index].id == w.id){
                currentWords[index] = w
            }
        }
    }

    fun clear(){
        currentWords = arrayListOf()
    }

}