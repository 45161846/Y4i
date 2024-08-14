package com.example.russian.architecture.repository

import com.example.russian.MyEnumClasses.TaskTopicType
import com.example.russian.architecture.data.olddata.dao.WordDao
import com.example.russian.architecture.data.olddata.entity.Word
import kotlinx.coroutines.flow.Flow

class LocalWordRepositoryImpl(
    override val dao: WordDao
): LocalWordRepository {

    override val dbWordFlow: Flow<List<Word>> = getAllWordsFromDB()
    override var cachedWords: List<Word> = emptyList()

    override fun getAllWordsFromDB(): Flow<List<Word>> {
        return dao.getAllWordsAsFlow()
    }

    override fun getWordsByTopic(topic: TaskTopicType): Flow<List<Word>> {
        return dao.getWordByTopicAsFlow(topic)
    }

    override fun getWordByID(id: Int): Flow<Word> {
        return dao.getWordByIdAsFlow(id)
    }

    override suspend fun addWord(word: Word) {
        dao.insert(word)
    }

    override suspend fun addWords(words: List<Word>) {
        dao.insert(words)
    }

    override suspend fun updateWord(word: Word) {
        dao.update(word)
    }

    override suspend fun updateWords(words: List<Word>) {
        words.forEach {word ->
            dao.update(word)
        }
    }

    override suspend fun removeByTopic(type: TaskTopicType) {
        dao.removeWordsByTopic(type)
    }

    override fun updateCache(list: List<Word>){
        cachedWords = list
    }

}