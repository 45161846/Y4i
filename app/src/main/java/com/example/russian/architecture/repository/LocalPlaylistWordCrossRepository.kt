package com.example.russian.architecture.repository

import com.example.russian.architecture.data.dao.PlaylistDao
import com.example.russian.architecture.data.dao.PlaylistWordCrossRefDao
import com.example.russian.architecture.data.entity.PlaylistWithWords
import com.example.russian.architecture.data.entity.PlaylistWordCrossRef
import com.example.russian.architecture.data.entity.Word
import kotlinx.coroutines.flow.Flow

interface LocalPlaylistWordCrossRepository{

    val crossDao: PlaylistWordCrossRefDao

    val playlistDao: PlaylistDao

    suspend fun addWordToPlaylist(wordId: Int, playlistId: Int)

    suspend fun addMultipleWordsToPlaylist(wordIds: List<Int>, playlistId: Int)

    suspend fun addMultipleByCrossRef(references: List<PlaylistWordCrossRef>)

    suspend fun removeWordFromPlaylist(wordId: Int, playlistId: Int)

    fun getAllPlaylists(): Flow<List<PlaylistWithWords>>

    fun getWordsInPlaylist(playlistId: Int): Flow<List<Word>>

    fun getWordsInPlaylist(playlistTitle: String): Flow<List<Word>>

}