package com.example.russian.architecture2.backend.initialloading.impl

import android.content.res.AssetManager
import com.example.russian.architecture2.backend.data.dao.LoadingDao
import com.example.russian.architecture2.backend.data.entity.Statistics
import com.example.russian.architecture2.backend.data.entity.TaskData
import com.example.russian.architecture2.backend.data.entity.playlist.Playlist
import com.example.russian.architecture2.backend.data.entity.playlist.PlaylistCrossRef
import com.example.russian.architecture2.backend.initialloading.arch.InitialLoadingExecutor
import com.example.russian.architecture2.mapper.FormatToNewWordMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InitialLoadingExecutor(
    private val dao: LoadingDao,
    private val assertManager: AssetManager,
) : InitialLoadingExecutor {


    override suspend fun execute() {

        withContext(Dispatchers.IO) {
            val loadingHandler = InitialLoadingFileReader(
                assertManager
            )

            val job1Completed = async {
                val doILoadInitialWords = dao.checkIfNoneWordExist()

                if (doILoadInitialWords) {
                    val initialWords = loadingHandler.getAllWords()

                    dao.addWords(initialWords)

                    val words = dao.getAllWordsList()

                    val mapper = FormatToNewWordMapper
                    val stats = List(words.size) {
                        Statistics(
                            wordId = words[it].id,
                            displayableText = mapper.getDisplayableText(words[it].value)
                        )
                    }

                    val taskData = List(words.size) {
                        TaskData(
                            wordId = words[it].id,
                            contextText = loadingHandler.contextWord(words[it].value),
                            displayableText = mapper.getDisplayableText(words[it].value)
                        )
                    }

                    val spellings = loadingHandler.getAllSpellingsToDBWords(words)

                    launch {
                        dao.addSpellings(spellings)
                    }
                    launch {
                        dao.addStats(stats)
                    }
                    launch {
                        dao.addTaskData(taskData)
                    }

                }
                true
            }

            val job2Completed = async {
                val doILoadInitialPlaylists = dao.checkIfNonePlaylistExist()

                if (doILoadInitialPlaylists) {
                    val initialPlaylists = initialPlaylists()
                    dao.addPlaylists(initialPlaylists)
                }
                true
            }

            //need to wait until words are added to DB
            job1Completed.await()
            job2Completed.await()
            //at this point words and playlists are added, so we can create their crossref
            launch {
                val doILoadInitialCrossRefConnections = dao.checkIfNoneCrossExist()

                if (doILoadInitialCrossRefConnections) {
                    val words = dao.getAllWordsList()

                    val crossRefs = List(words.size) {

                        //id in crossRef DB starts from 1, so topic value correlates with default playlist
                        PlaylistCrossRef(
                            wordId = words[it].id,
                            playlistId = (words[it].topic + 1).toLong()
                        )
                    }

                    dao.addMultipleCrossRef(crossRefs)
                }
            }
        }

    }


    private fun initialPlaylists() = listOf(
        Playlist(title = "Наречия ФИПИ", capacity = 0),
        Playlist(title = "Паронимы ФИПИ", capacity = 0),
        Playlist(title = "Ударения ФИПИ", capacity = 0)
    )
}