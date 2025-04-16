package com.example.russian.game.repository.impl

import com.example.russian.game.back.data.dao.GameDao
import com.example.russian.game.back.data.entity.MyTask
import com.example.russian.game.back.data.entity.PartOfTaskWithSpellingVariants
import com.example.russian.game.back.data.entity.TaskPartOfTask
import com.example.russian.game.repository.arch.AnswerAPI
import com.example.russian.game.repository.arch.GameRepositoryInterface
import com.example.russian.main.Id
import com.example.russian.main.Time
import com.example.russian.main.data.local.db.entity.Answer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Random

class GameRepository : GameRepositoryInterface {

    private lateinit var dao: GameDao

    private var cachedWords = listOf<MyTask>()

    private var taskStartTime = Time()

    override fun setDao(dao: GameDao) {
        this.dao = dao
    }

    override fun allWordsInPlaylist(playlistId: Id): Flow<List<MyTask>> {
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

    override suspend fun saveAnswer(taskId: Id, answerAPI: AnswerAPI) {

        val answerTime = System.currentTimeMillis()

        val lastStats = dao.getStats(taskId)

        val answer = if (answerAPI.correct()) {
            dao.updateStats(taskId, lastStats.correct + 1, lastStats.attempts + 1)
            Answer(
                taskId = taskId,
                correct = true,
                time = Time(answerTime),
                solveTime = Time(answerTime - taskStartTime.value)
            )
        } else {
            dao.updateStats(taskId, lastStats.correct, lastStats.attempts + 1)
            Answer(
                taskId = taskId,
                correct = false,
                time = Time(answerTime),
                solveTime = Time(answerTime - taskStartTime.value)
            )
        }
        dao.addAnswer(answer)
    }

    override fun randomWord(): MyTask {
        taskStartTime = Time()
        val randomGenerator = Random(System.currentTimeMillis())
        val ind = randomGenerator.nextInt(cachedWords.size)
        return cachedWords[ind]
    }

    override fun displayableWord(taskId: Id): Flow<TaskPartOfTask> {
        return dao.getWordWithTask(taskId)
    }

    override fun isEmpty(): Boolean {
        return cachedWords.isEmpty()
    }

    override suspend fun partsAndSpellings(taskId: Id): List<PartOfTaskWithSpellingVariants> {
        return dao.getSpellingsAndParts(taskId).sortedBy { it.partOfTask.index }
    }

    override suspend fun answer(answer: Answer) {
        dao.addAnswer(answer)
    }
}