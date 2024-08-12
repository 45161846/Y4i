package com.example.russian.gameClasses

import android.app.Application
import com.example.russian.architecture.data.entity.Word
import com.example.russian.architecture.data.dao.WordDao
import com.example.russian.architecture.data.db.WordPlaylistDatabase
import com.example.russian.database.WordDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyGameViewModelImpl(
    application: Application,
    topic: Int,
    gameSettings: GameSettings
): MyGameViewModelArch(application, topic, gameSettings) {

    private var DB: WordPlaylistDatabase = WordPlaylistDatabase.getWordPlaylistDB(application)
    private var dao: WordDao = DB.wordDao()

    override suspend fun updateWordInDB(value: Word?) {
        dao.update(value!!)

    }

    override suspend fun changeWordAfterAnswer(value: Word?, isAnswerCorrect: Boolean) {
        val new_percentage: Float
        val new_right: Int = when(isAnswerCorrect){
            true -> value!!.gotItRight + 1

            false -> value!!.gotItRight
        }
        val new_attempts: Int = value.attempts + 1
        new_percentage = (new_right.toFloat() / new_attempts.toFloat())
        val w = Word(
            id = value.id,
            value = value.value,
            topic = value.topic,
            right = new_right,
            attempts = new_attempts,
            percentage = new_percentage
        )
        updateWordInDB(w)
        withContext(Dispatchers.Main){
            repository.updateWord(w)
        }

    }

    override suspend fun getWordsFromDB(topic: Int): List<Word>{
        return withContext(Dispatchers.IO) {
            dao.getWordByTopic(topic)
        }

    }
}