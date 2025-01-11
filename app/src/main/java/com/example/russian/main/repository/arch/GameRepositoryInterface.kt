package com.example.russian.main.repository.arch

import com.example.russian.main.back.data.entity.MyTask
import com.example.russian.main.back.data.entity.PartOfTaskWithSpellingVariants
import com.example.russian.main.back.data.entity.TaskPartOfTask
import com.example.russian.main.application.MyApplication
import kotlinx.coroutines.flow.Flow

interface GameRepositoryInterface {

    fun setDao(application: MyApplication)

    fun allWordsInPlaylist(playlistId: Long) : Flow<List<MyTask>>

    fun cacheWords(words: List<MyTask>)

    suspend fun saveAnswer(taskId: Long, answerAPI: AnswerAPI)

    fun randomWord(): MyTask

    fun displayableWord(taskId: Long): Flow<TaskPartOfTask>

    fun isEmpty(): Boolean

    suspend fun partsAndSpellings(taskId: Long): List<PartOfTaskWithSpellingVariants>
}

interface AnswerAPI{
    fun correct(): Boolean
}