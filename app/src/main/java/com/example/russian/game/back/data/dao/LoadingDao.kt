package com.example.russian.game.back.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.russian.main.TaskType
import com.example.russian.game.back.data.entity.MyTask
import com.example.russian.game.back.data.entity.PartOfTask
import com.example.russian.game.back.data.entity.SpellingVariant
import com.example.russian.game.back.data.entity.Statistics
import com.example.russian.game.back.data.entity.TaskData
import com.example.russian.game.back.data.entity.TaskPartOfTask
import com.example.russian.game.back.data.entity.playlist.Playlist
import com.example.russian.game.back.data.entity.playlist.PlaylistCrossRef


@Dao
interface LoadingDao {

    @Insert
    suspend fun addStats(stats: List<Statistics>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPartOfTasks(PartOfTasks: List<PartOfTask>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTaskData(tasks: List<TaskData>)

    @Insert
    suspend fun addPlaylists(playlists: List<Playlist>)

    @Insert
    suspend fun addWords(words: List<MyTask>)

    @Insert
    suspend fun addSpellings(spellings: List<SpellingVariant>)

    @Query("SELECT (SELECT COUNT(*) FROM PLAYLISTCROSSREF) == 0")
    suspend fun checkIfNoneCrossExist(): Boolean

    @Query("SELECT (SELECT COUNT(*) FROM MyTask) == 0")
    suspend fun checkIfNoneWordExist(): Boolean

    @Query("SELECT (SELECT COUNT(*) FROM `new-playlist`) == 0")
    fun checkIfNonePlaylistExist(): Boolean

    @Query("SELECT * FROM MyTask")
    suspend fun getAllWordsList(): List<MyTask>

    @Transaction
    @Query("SELECT * FROM MyTask WHERE `topic` = :type")
    suspend fun getAllClickableTasks(type: TaskType = TaskType.CLICKABLE): List<TaskPartOfTask>

    suspend fun addMultipleCrossRef(crossRefs: List<PlaylistCrossRef>) {
        val increaseValues = mutableMapOf<Long, Long>()

        crossRefs.forEach { ref ->
            increaseValues[ref.playlistId.value] = increaseValues.getOrDefault(ref.playlistId.value, 0) + 1
        }

        addCrossRefs(crossRefs)

        increaseValues.forEach{pair ->
            changeCapacity(pair.key, pair.value)
        }
    }

    @Insert
    suspend fun addCrossRefs(crossRef: List<PlaylistCrossRef>)

    @Query("UPDATE `new-playlist` SET `capacity` = :newCapacity WHERE `playlistId` = :id")
    suspend fun changeCapacity(id: Long, newCapacity: Long)
}