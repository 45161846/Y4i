package com.example.russian.gameClasses.repo

import com.example.russian.architecture.data.dao.PlaylistWordCrossRefDao
import com.example.russian.architecture.data.dao.SpellingDao
import com.example.russian.architecture.data.dao.WordDao
import com.example.russian.architecture.data.entity.PlaylistWithWords
import com.example.russian.architecture.data.entity.Word
import com.example.russian.architecture.data.entity.WordWithSpellings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class WordSpellingRepositoryImpl(
    private val wordDao: WordDao,
    private val playlistCrossRefDao: PlaylistWordCrossRefDao,
    private val spellingDao: SpellingDao,
): WordSpellingRepositoryArch {

    override var savedWords: List<Word>? = listOf()

    private var wordsFlow = flowOf<List<Word>>()

    private fun getPlaylistWithWords(playlistId: Int): Flow<PlaylistWithWords>
        = playlistCrossRefDao.getPlaylistWithWords(playlistId)


    override suspend fun loadGamePlaylist(playlistId: Int) {

        wordsFlow = getPlaylistWithWords(playlistId).map {
            it.words
        }

        getPlaylistWithWords(playlistId).map {
            it.words
        }.collect {
            savedWords = it
        }
    }

    override fun getCurrentSpelling(wordId: Int): Flow<WordWithSpellings>{
        return spellingDao.getWordSpelling(wordId)
    }

    override suspend fun answerSaveResult(result: Result, wordId: Int) {

        when(result){
            Result.CORRECT ->{
                wordDao.update(wordId, 1)
            }
            Result.INCORRECT -> {
                wordDao.update(wordId, 0)
            }
        }
    }

    override fun wordsFlow(): Flow<List<Word>> = wordsFlow

    override fun getRandomWordWithSpellingFlow(): Flow<WordWithSpellings> {
        val value = savedWords ?: emptyList()

        if(value.isNotEmpty()){
            return spellingDao.getWordSpelling(value.random().id)
        }
        return flowOf()
    }

}