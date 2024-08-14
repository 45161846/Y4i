package com.example.russian.architecture2.repository.impl

import com.example.russian.architecture2.application.MyApplication
import com.example.russian.architecture2.backend.data.dao.GameDao
import com.example.russian.architecture2.backend.data.entity.NewWord
import com.example.russian.architecture2.backend.data.entity.WordTaskSpelling
import com.example.russian.architecture2.repository.arch.AnswerAPI
import com.example.russian.architecture2.repository.arch.GameRepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GameRepository : GameRepositoryInterface {

    private lateinit var dao: GameDao

    private var cachedWords = listOf<NewWord>()

    override fun setDao(application: MyApplication) {

        dao = application.gameDao()
    }

    override fun allWordsInPlaylist(playlistId: Long): Flow<List<NewWord>> {
        return dao.getPlaylistWithWords(
            playlistId
        ).map { playlist ->
            playlist?.let {
                playlist.words
            } ?: listOf()
        }
    }


    override fun cacheWords(words: List<NewWord>) {
        cachedWords = words
    }

    override suspend fun saveAnswer(wordId: Long, answerAPI: AnswerAPI) {

        val lastStats = dao.getStats(wordId)

        if (answerAPI.correct()) {
            dao.updateStats(wordId, lastStats.correct + 1, lastStats.attempts + 1)
        } else {
            dao.updateStats(wordId, lastStats.correct, lastStats.attempts + 1)
        }
    }

    override fun randomWord(): NewWord {
        return cachedWords.random()
    }

    override fun displayableWord(wordId: Long): Flow<WordTaskSpelling> {
        return dao.getWordWithTask(wordId)
    }

    override fun isEmpty(): Boolean {
        return cachedWords.isEmpty()
    }
}