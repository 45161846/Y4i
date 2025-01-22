package com.example.russian.game.repository.arch

import com.example.russian.main.Id
import com.example.russian.game.back.data.dao.GameDao
import com.example.russian.game.back.data.entity.MyTask
import com.example.russian.game.back.data.entity.PartOfTaskWithSpellingVariants
import com.example.russian.game.back.data.entity.TaskPartOfTask
import com.example.russian.main.data.local.db.entity.Answer
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

    suspend fun answer(answer: Answer)
}

interface AnswerAPI{
    fun correct(): Boolean
}