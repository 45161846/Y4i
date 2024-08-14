package com.example.russian.gameClasses.repo

import com.example.russian.architecture.data.olddata.entity.Word
import com.example.russian.architecture.data.olddata.entity.WordWithSpellings
import kotlinx.coroutines.flow.Flow

interface WordSpellingRepositoryArch {

    var savedWords:List<Word>?

    suspend fun loadGamePlaylist(playlistId: Int)

    fun getRandomWordWithSpellingFlow(): Flow<WordWithSpellings>

    fun getCurrentSpelling(wordId: Int): Flow<WordWithSpellings>

    suspend fun answerSaveResult(result: Result, wordId: Int)

    fun wordsFlow(): Flow<List<Word>>
}

enum class Result {
    CORRECT, INCORRECT
}