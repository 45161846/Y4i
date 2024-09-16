package com.example.russian.repository.arch

import com.example.russian.application.MyApplication
import com.example.russian.back.data.entity.NewWord
import com.example.russian.back.data.entity.WordTaskSpelling
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