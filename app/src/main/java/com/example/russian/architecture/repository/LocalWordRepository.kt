package com.example.russian.architecture.repository

import com.example.russian.MyEnumClasses.MyFilterSettings
import com.example.russian.MyEnumClasses.MyFilterSettingsArch
import com.example.russian.MyEnumClasses.TaskTopicType
import com.example.russian.architecture.data.olddata.entity.Word
import com.example.russian.architecture.data.olddata.dao.WordDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import java.util.logging.Filter

interface LocalWordRepository {

    val dbWordFlow: Flow<List<Word>>

    var cachedWords: List<Word>

    val dao: WordDao

    //Database ..................................................................................
    //get
    fun getAllWordsFromDB(): Flow<List<Word>>

    fun getWordsByTopic(topic: TaskTopicType) : Flow<List<Word>>

    fun getWordByID(id: Int): Flow<Word>

    //add
    suspend fun addWord(word: Word)

    suspend fun addWords(words: List<Word>)

    //update
    suspend fun updateWord(word: Word)

    suspend fun updateWords(words: List<Word>)

    //remove
    suspend fun removeByTopic(type: TaskTopicType)
    //...........................................................................................

    fun updateCache(list: List<Word>)

}
