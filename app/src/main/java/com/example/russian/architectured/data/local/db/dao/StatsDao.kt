package com.example.russian.architectured.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.russian.architectured.Counter
import com.example.russian.architectured.Id
import com.example.russian.architectured.TaskType
import com.example.russian.architectured.data.local.db.entity.TaskStat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlin.random.Random


@Dao
interface StatsDao {

    @Query("SELECT * FROM TASKSTAT WHERE id = :id")
    suspend fun statById(id: Id): TaskStat

//    @Query("SELECT * FROM TASKSTAT")
//    fun observeAll(): Flow<List<TaskStat>>

    fun observeAllStats(): Flow<List<TaskStat>>{

        val randInt = {
            Random.nextInt(0, 10)
        }

        return flowOf(
            List(100){
                TaskStat(
                    Id(it.toLong()),
                    randInt(),
                    randInt(),
                    "Word $it",
                    TaskType.entries.toTypedArray().random()
                )
            }
        )
    }

}