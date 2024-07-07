package com.example.russian.architecture.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.russian.architecture.data.entity.PlaylistWithWords
import com.example.russian.architecture.data.entity.PlaylistWordCrossRef
import kotlinx.coroutines.flow.Flow


@Dao
interface PlaylistWordCrossRefDao { // работает со всеми взаимоотношениями Word - Playlist

    @Transaction
    @Query("SELECT * FROM playlist")
    fun getAllPlaylists(): Flow<List<PlaylistWithWords>>

    @Transaction
    @Query("SELECT * FROM playlist WHERE `playlistId` = :playlistId")
    fun getPlaylistWithWords(playlistId: Int): Flow<PlaylistWithWords>

    @Transaction
    @Query("SELECT * FROM playlist WHERE `title` = :playlistTitle")
    fun getPlaylistWithWords(playlistTitle: String): Flow<PlaylistWithWords>

    @Query("SELECT (SELECT COUNT(*) FROM playlistwordcrossref) == 0")
    suspend fun checkIfNoneConnectionsExist(): Boolean

    @Insert
    suspend fun addWordToPlaylistConnection(cross: PlaylistWordCrossRef)

    @Insert
    suspend fun addMultipleWordToPlaylistConnection(crossList: List<PlaylistWordCrossRef>)

    @Query("DELETE FROM playlistwordcrossref WHERE `playlistId` = :playlistId AND `wordId` = :wordId")
    suspend fun removeWordFromPlaylist(wordId: Int, playlistId: Int)
}