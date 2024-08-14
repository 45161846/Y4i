package com.example.russian.architecture2.backend.initialloading.arch

import com.example.russian.architecture2.backend.data.entity.NewWord
import com.example.russian.architecture2.backend.data.entity.Spelling

interface InitialLoadingFileReader {

    suspend fun getAllWords(): List<NewWord>

    suspend fun getAllSpellingsToDBWords(words: List<NewWord>): List<Spelling>

    fun contextWord(inputValue: String): String
}