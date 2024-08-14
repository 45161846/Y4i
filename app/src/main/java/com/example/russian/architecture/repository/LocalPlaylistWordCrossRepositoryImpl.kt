package com.example.russian.architecture.repository

import com.example.russian.architecture.data.olddata.dao.PlaylistDao
import com.example.russian.architecture.data.olddata.dao.PlaylistWordCrossRefDao
import com.example.russian.architecture.data.olddata.entity.PlaylistWithWords
import com.example.russian.architecture.data.olddata.entity.PlaylistWordCrossRef
import com.example.russian.architecture.data.olddata.entity.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalPlaylistWordCrossRepositoryImpl(
    override val crossDao: PlaylistWordCrossRefDao,
    override val playlistDao: PlaylistDao
) : LocalPlaylistWordCrossRepository {
    override suspend fun addWordToPlaylist(wordId: Int, playlistId: Int) {
        crossDao.addWordToPlaylistConnection(PlaylistWordCrossRef(wordId = wordId, playlistId = playlistId))
        playlistDao.increaseCapacity(id = playlistId, increaseValue = 1)
    }

    override suspend fun addMultipleWordsToPlaylist(wordIds: List<Int>, playlistId: Int) {
        val connections = List(wordIds.size){
            PlaylistWordCrossRef(wordId = wordIds[it], playlistId)
        }

        crossDao.addMultipleWordToPlaylistConnection(connections)
        playlistDao.increaseCapacity(playlistId, increaseValue =  wordIds.size)
    }

    override suspend fun addMultipleByCrossRef(references: List<PlaylistWordCrossRef>) {

        val increaseValues = mutableMapOf<Int, Int>()

        references.forEach { ref ->
            increaseValues[ref.playlistId] = increaseValues.getOrDefault(ref.playlistId, 0) + 1
        }

        crossDao.addMultipleWordToPlaylistConnection(references)

        increaseValues.forEach{pair ->
            playlistDao.increaseCapacity(pair.key, pair.value)
        }
    }

    override suspend fun removeWordFromPlaylist(wordId: Int, playlistId: Int) {
        crossDao.removeWordFromPlaylist(wordId = wordId, playlistId = playlistId)
        playlistDao.increaseCapacity(id = playlistId, increaseValue = -1)
    }

    override fun getAllPlaylists(): Flow<List<PlaylistWithWords>> {
        return crossDao.getAllPlaylists()
    }

    override fun getWordsInPlaylist(playlistId: Int): Flow<List<Word>> {
        val withWords = crossDao.getPlaylistWithWords(playlistId)
        return withWords.map {
            it.words
        }
    }

    override fun getWordsInPlaylist(playlistTitle: String): Flow<List<Word>> {
        val withWords = crossDao.getPlaylistWithWords(playlistTitle)
        return withWords.map {
            it.words
        }
    }
}