package com.example.russian.main.repository.arch

import com.example.russian.architectured.Id
import com.example.russian.main.back.data.entity.MyTask
import com.example.russian.main.back.data.entity.PartOfTaskWithSpellingVariants
import com.example.russian.main.back.data.entity.TaskPartOfTask
import com.example.russian.main.application.MyApplication
import com.example.russian.main.back.data.dao.GameDao
import kotlinx.coroutines.flow.Flow

interface GameRepositoryInterface {

    fun setDao(dao: GameDao)

    fun allWordsInPlaylist(playlistId: Id) : Flow<List<MyTask>>

    fun cacheWords(words: List<MyTask>)

    suspend fun saveAnswer(taskId: Id, answerAPI: AnswerAPI)

    fun randomWord(): MyTask

    fun displayableWord(taskId: Id): Flow<TaskPartOfTask>

    fun isEmpty(): Boolean

    suspend fun partsAndSpellings(taskId: Id): List<PartOfTaskWithSpellingVariants>
}

interface AnswerAPI{
    fun correct(): Boolean
}