package com.example.russian.mainScreenPackage.repository

import com.example.russian.architecture.data.olddata.entity.Word

interface WordsRepositoryInterface {

    suspend fun setWords(words: List<Word>): Boolean
    fun updateWord(w: Word)
    fun clear()

}