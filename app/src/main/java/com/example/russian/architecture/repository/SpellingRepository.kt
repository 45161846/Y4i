package com.example.russian.architecture.repository

import com.example.russian.architecture.data.olddata.entity.Spelling

interface SpellingRepository {

    fun isSpellingCorrect(spellingId: Int): Boolean

    fun getWordSpellings(wordId: Int): List<Spelling>

    fun addSpellingVariant(spelling: Spelling)

    fun addSpellingVariants(spellings: List<Spelling>)

}