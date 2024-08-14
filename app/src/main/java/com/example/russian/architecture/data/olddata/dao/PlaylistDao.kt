package com.example.russian.architecture.data.olddata.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.russian.architecture.data.olddata.entity.Playlist
import kotlinx.coroutines.flow.Flow


@Dao
interface PlaylistDao { //отвечает только за операции с экземплярами Playlist

    @Insert
    suspend fun addPlaylist(playlist: Playlist)

    @Insert
    suspend fun addPlaylists(playlists: List<Playlist>)

    @Query("SELECT (SELECT COUNT(*) FROM Playlist) == 0")
    suspend fun checkIfNoneExist(): Boolean

    @Query("SELECT * FROM Playlist")
    fun getAll(): Flow<List<Playlist>>

    @Query("SELECT * FROM PLAYLIST WHERE `playlistId` = :id LIMIT 1")
    fun getPlaylistById(id: Int): Flow<Playlist>

    @Query("SELECT * FROM PLAYLIST WHERE `title` LIKE (:pref + '%')")
    fun getWithPrefix(pref: String): Flow<List<Playlist>>

    @Query("UPDATE PLAYLIST SET `capacity` = :newCapacity WHERE `playlistId` = :id")
    suspend fun updateCapacity(id: Int, newCapacity: Int)

    @Query("UPDATE PLAYLIST SET `capacity` = :newCapacity WHERE `title` = :title")
    suspend fun updateCapacity(title: String, newCapacity: Int)

    @Query("UPDATE Playlist SET capacity = capacity + :increaseValue WHERE `playlistId` = :id")
    suspend fun increaseCapacity(id: Int, increaseValue: Int)

    @Query("UPDATE Playlist SET capacity = capacity + :increaseValue WHERE `title` = :title")
    suspend fun increaseCapacity(title: String, increaseValue: Int)

    @Query("DELETE FROM PLAYLIST WHERE `playlistId` = :id")
    suspend fun remove(id: Int)

    @Query("DELETE FROM PLAYLIST WHERE `title` = :title")
    suspend fun remove(title: String)
}