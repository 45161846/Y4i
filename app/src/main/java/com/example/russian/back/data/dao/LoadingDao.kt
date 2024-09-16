package com.example.russian.back.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.russian.back.data.entity.NewWord
import com.example.russian.back.data.entity.Spelling
import com.example.russian.back.data.entity.Statistics
import com.example.russian.back.data.entity.TaskData
import com.example.russian.back.data.entity.playlist.Playlist
import com.example.russian.back.data.entity.playlist.PlaylistCrossRef


@Dao
interface LoadingDao {

    @Insert
    suspend fun addStats(stats: List<Statistics>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSpellings(spellings: List<Spelling>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTaskData(tasks: List<TaskData>)

    @Insert
    suspend fun addPlaylists(playlists: List<Playlist>)

    @Insert
    suspend fun addWords(words: List<NewWord>)

    @Query("SELECT (SELECT COUNT(*) FROM PLAYLISTCROSSREF) == 0")
    suspend fun checkIfNoneCrossExist(): Boolean

    @Query("SELECT (SELECT COUNT(*) FROM NewWord) == 0")
    suspend fun checkIfNoneWordExist(): Boolean

    @Query("SELECT (SELECT COUNT(*) FROM `new-playlist`) == 0")
    fun checkIfNonePlaylistExist(): Boolean

    @Query("SELECT * FROM NEWWORD")
    suspend fun getAllWordsList(): List<NewWord>

    suspend fun addMultipleCrossRef(crossRefs: List<PlaylistCrossRef>) {
        val increaseValues = mutableMapOf<Long, Long>()

        crossRefs.forEach { ref ->
            increaseValues[ref.playlistId] = increaseValues.getOrDefault(ref.playlistId, 0) + 1
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