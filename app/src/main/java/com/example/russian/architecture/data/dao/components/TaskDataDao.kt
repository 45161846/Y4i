package com.example.russian.architecture.data.dao.components

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import com.example.russian.architecture.data.entity.Spelling
import com.example.russian.architecture.data.entity.components.NewWord
import com.example.russian.architecture.data.entity.components.TaskData


@Dao
interface TaskDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTaskData(task: TaskData)

    @Query("SELECT * FROM TaskData WHERE `wordId` = :wordId LIMIT 1")
    suspend fun getTask(wordId: Int): TaskData

    @Transaction
    @Query("SELECT * FROM NewWord WHERE `new-wordId` = :wordId LIMIT 1")
    suspend fun getWordWithTask(wordId: Int) : WordTaskSpelling

}


class WordTaskNoSpelling(
    @Embedded
    val word: NewWord,

    @Relation(
        entity = TaskData::class,
        parentColumn = "new-wordId",
        entityColumn = "wordId"
    )
    val taskData: TaskData
)

class WordTaskSpelling(
    @Embedded
    val wordTask: WordTaskNoSpelling,

    @Relation(
        parentColumn = "new-wordId",
        entityColumn = "wordId"
    )
    val spellings: List<Spelling>
)