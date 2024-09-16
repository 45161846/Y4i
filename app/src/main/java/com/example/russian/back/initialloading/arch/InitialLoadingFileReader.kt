package com.example.russian.back.initialloading.arch

import com.example.russian.back.data.entity.NewWord
import com.example.russian.back.data.entity.Spelling

interface InitialLoadingFileReader {

    suspend fun getAllWords(): List<NewWord>

    suspend fun getAllSpellingsToDBWords(words: List<NewWord>): List<Spelling>

    fun contextWord(inputValue: String): String
}