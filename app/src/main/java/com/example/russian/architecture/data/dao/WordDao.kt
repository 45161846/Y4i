package com.example.russian.architecture.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.russian.MyEnumClasses.TaskTopicType
import com.example.russian.architecture.data.entity.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Insert
    suspend fun insert(word: Word)

    @Insert
    suspend fun insert(words: List<Word>)

    @Query("SELECT (SELECT COUNT(*) FROM WORD) == 0")
    suspend fun checkIfNoneExist():Boolean

    @Query("SELECT * FROM word")
    suspend fun getAllWords(): List<Word>

    @Query("SELECT * FROM word")
    fun getAllWordsAsFlow(): Flow<List<Word>>

    @Query("SELECT * FROM word WHERE `wordId` = :requested_id LIMIT 1")
    suspend fun getWordById(requested_id: Int): Word

    @Query("SELECT * FROM word WHERE `wordId` = :requested_id LIMIT 1")
    fun getWordByIdAsFlow(requested_id: Int): Flow<Word>

    @Query("SELECT * FROM word WHERE `value` = :value LIMIT 1")
    suspend fun getWordByValue(value: String): Word

    @Query("SELECT * FROM word WHERE `value` = :value LIMIT 1")
    fun getWordByValueAsFlow(value: String): Flow<Word>

    @Query("SELECT * FROM word WHERE `topic` = :topic")
    suspend fun getWordByTopic(topic: Int): List<Word>

    @Query("SELECT * FROM word WHERE `topic` = :topic")
    fun getWordByTopicAsFlow(topic: Int): Flow<List<Word>>

    @Query("SELECT * FROM word WHERE `displayable-text` LIKE (:pref + '%')")
    fun getWordsWithPrefAsFlow(pref: String): Flow<List<Word>>

    @Query("SELECT * FROM word WHERE `topic` = :topic AND `displayable-text` LIKE (:pref + '%')")
    fun getWordsByTopicWithPref(pref: String, topic: Int): Flow<List<Word>>

    @Query("UPDATE word SET `right` = `right` + :difference, `attempts` = `attempts` + 1, `percentage` = :percent WHERE wordId = :word_id")
    suspend fun update(word_id: Int, difference: Int, percent: Float)

    @Query("UPDATE word SET `right` = :right, `attempts` = :attempts, `percentage` = :percent WHERE wordId = :word_id")
    suspend fun updateNoCalculation(word_id: Int, right: Int, attempts: Int, percent: Float)

    @Query("UPDATE word SET `right` = `right` + :difference, `attempts` = `attempts` + 1 WHERE wordId = :word_id")
    suspend fun update(word_id: Int, difference: Int)

    @Update
    suspend fun update(word: Word)

    @Query("SELECT COUNT(wordId) FROM word WHERE `topic` = :topic")
    suspend fun countTopicTasks(topic: Int): Int

    @Query("SELECT wordId FROM word WHERE topic = :topic")
    suspend fun getIdByTopic(topic: Int): List<Int>

    @Query("DELETE FROM WORD WHERE `wordId` IN (:idList)")
    suspend fun removeWords(idList: List<Int>)

    @Query("DELETE FROM WORD WHERE `topic` = :topic")
    suspend fun removeWordsByTopic(topic: TaskTopicType)

}