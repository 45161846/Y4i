package com.example.russian.mainScreenPackage

import com.example.russian.database.Word

interface WordsRepositoryInterface {

    suspend fun setWords(words: List<Word>): Boolean
    fun updateWord(w: Word)
    fun clear()

}