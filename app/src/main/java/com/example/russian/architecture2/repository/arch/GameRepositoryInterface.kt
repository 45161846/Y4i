package com.example.russian.architecture2.repository.arch

import com.example.russian.architecture2.application.MyApplication
import com.example.russian.architecture2.backend.data.entity.NewWord
import com.example.russian.architecture2.backend.data.entity.WordTaskSpelling
import kotlinx.coroutines.flow.Flow

interface GameRepositoryInterface {

    fun setDao(application: MyApplication)

    fun allWordsInPlaylist(playlistId: Long) : Flow<List<NewWord>>

    fun cacheWords(words: List<NewWord>)

    suspend fun saveAnswer(wordId: Long, answerAPI: AnswerAPI)

    fun randomWord(): NewWord

    fun displayableWord(wordId: Long): Flow<WordTaskSpelling>

    fun isEmpty(): Boolean
}

interface AnswerAPI{
    fun correct(): Boolean
}