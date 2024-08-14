package com.example.russian.architecture.initialloading

import android.content.res.AssetManager
import com.example.russian.architecture.data.olddata.db.WordPlaylistDatabase
import com.example.russian.architecture.data.olddata.entity.PlaylistWordCrossRef
import com.example.russian.architecture.data.olddata.entity.Spelling
import com.example.russian.architecture.data.olddata.entity.initialPlaylists
import com.example.russian.architecture.repository.LocalPlaylistWordCrossRepositoryImpl
import com.example.russian.toolPackage.WordToTaskMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InitialLoadingExecutorImpl(//при необходимости выполняет загрузку дефолтных данных
    override val dataBase: WordPlaylistDatabase,
    override val assertManager: AssetManager,
    override val checkFileName: String
):InitialLoadingExecutor {

    override suspend fun execute() {

        val wordDao = dataBase.wordDao()
        val playlistDao = dataBase.playlistDao()
        val crossDao = dataBase.crossRefDao()
        val spellingDao = dataBase.spellingDao()

        withContext(Dispatchers.IO){
            val loadingHandler = InitialLoadingFileReaderImpl(
                assertManager = assertManager,
                checkFileName = checkFileName
            )

            val job1Completed = async {
                val doILoadInitialWords = wordDao.checkIfNoneExist()

                if(doILoadInitialWords) {
                    val initialWords = loadingHandler.getAllWords()

                    wordDao.insert(initialWords)

                    val words = wordDao.getAllWords()

                    val spellings = loadingHandler.getAllSpellingsToDBWords(words)

                    spellingDao.addSpellings(spellings)
                }
                true
            }

            val job2Completed = async {
                val doILoadInitialPlaylists = playlistDao.checkIfNoneExist()

                if(doILoadInitialPlaylists){
                    val initialPlaylists = initialPlaylists()
                    playlistDao.addPlaylists(initialPlaylists)
                }
                true
            }

            //need to wait until words are added to DB
            job1Completed.await()

//            launch {
//                val words = wordDao.getAllWords()
//
//                val spellings = loadingHandler.getAllSpellingsToDBWords(words)
//
//                spellingDao.addSpellings(spellings)
//            }

            job2Completed.await()
            //at this point words and playlists are added, so we can create their crossref
            launch {
                val doILoadInitialCrossRefConnections = crossDao.checkIfNoneConnectionsExist()

                if(doILoadInitialCrossRefConnections){
                    val words = wordDao.getAllWords()

                    val crossRefs = List(words.size){

                        //id in crossRef DB starts from 1, so topic value correlates with default playlist
                        PlaylistWordCrossRef(words[it].id, words[it].topic + 1)
                    }

                    LocalPlaylistWordCrossRepositoryImpl(
                        crossDao,
                        playlistDao
                    ).addMultipleByCrossRef(crossRefs)
                }
            }
        }
    }
}