package com.example.russian.main.repository.impl

import com.example.russian.main.back.data.dao.GameDao
import com.example.russian.main.back.data.entity.MyTask
import com.example.russian.main.back.data.entity.PartOfTaskWithSpellingVariants
import com.example.russian.main.back.data.entity.TaskPartOfTask
import com.example.russian.main.application.MyApplication
import com.example.russian.main.repository.arch.AnswerAPI
import com.example.russian.main.repository.arch.GameRepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Random

class GameRepository : GameRepositoryInterface {

    private lateinit var dao: GameDao

    private var cachedWords = listOf<MyTask>()

    override fun setDao(application: MyApplication) {
        dao = application.gameDao()
    }

    override fun allWordsInPlaylist(playlistId: Long): Flow<List<MyTask>> {
        return dao.getPlaylistWithWords(
            playlistId
        ).map { playlist ->
            playlist?.let {
                playlist.words
            } ?: listOf()
        }
    }


    override fun cacheWords(words: List<MyTask>) {
        cachedWords = words
    }

    override suspend fun saveAnswer(taskId: Long, answerAPI: AnswerAPI) {

        val lastStats = dao.getStats(taskId)

        if (answerAPI.correct()) {
            dao.updateStats(taskId, lastStats.correct + 1, lastStats.attempts + 1)
        } else {
            dao.updateStats(taskId, lastStats.correct, lastStats.attempts + 1)
        }
    }

    override fun randomWord(): MyTask {
        val randomGenerator = Random(System.currentTimeMillis())
        val ind = randomGenerator.nextInt(cachedWords.size)
        return cachedWords[ind]
    }

    override fun displayableWord(taskId: Long): Flow<TaskPartOfTask> {
        return dao.getWordWithTask(taskId)
    }

    override fun isEmpty(): Boolean {
        return cachedWords.isEmpty()
    }

    override suspend fun partsAndSpellings(taskId: Long): List<PartOfTaskWithSpellingVariants> {
        return dao.getSpellingsAndParts(taskId).sortedBy { it.partOfTask.index }
    }
}