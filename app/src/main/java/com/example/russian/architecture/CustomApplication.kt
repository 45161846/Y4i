package com.example.russian.architecture

import android.app.Application
import com.example.russian.R
import com.example.russian.architecture.data.olddata.db.WordPlaylistDatabase
import com.example.russian.architecture.initialloading.InitialLoadingExecutorImpl
import com.example.russian.architecture.repository.LocalPlaylistRepositoryImpl
import com.example.russian.architecture.repository.LocalPlaylistWordCrossRepositoryImpl
import com.example.russian.architecture.repository.LocalWordRepositoryImpl
import com.example.russian.gameClasses.repo.WordSpellingRepositoryArch
import com.example.russian.gameClasses.repo.WordSpellingRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomApplication: Application() {

    private lateinit var dataBase : WordPlaylistDatabase

    private lateinit var wordRepository: LocalWordRepositoryImpl

    private lateinit var playlistRepository: LocalPlaylistRepositoryImpl

    private lateinit var crossRepositoryImpl: LocalPlaylistWordCrossRepositoryImpl

    private lateinit var wordSpellingRepository: WordSpellingRepositoryArch

    lateinit var statsViewmodel: StatsScreenViewModelImpl

    override fun onCreate() {
        super.onCreate()

        dataBase = WordPlaylistDatabase.getWordPlaylistDB(applicationContext)

        CoroutineScope(Dispatchers.IO).launch {
            InitialLoadingExecutorImpl(
                dataBase,
                assertManager = assets,
                checkFileName = getString(R.string.files_data)
            ).execute()
        }

        wordRepository = LocalWordRepositoryImpl(dataBase.wordDao())
        playlistRepository = LocalPlaylistRepositoryImpl(dataBase.playlistDao())
        crossRepositoryImpl = LocalPlaylistWordCrossRepositoryImpl(dataBase.crossRefDao(), dataBase.playlistDao())

        wordSpellingRepository = WordSpellingRepositoryImpl(
            dataBase.wordDao(),
            dataBase.crossRefDao(),
            dataBase.spellingDao()
        )

        statsViewmodel = StatsScreenViewModelImpl()
    }

    fun wordRepo() = wordRepository

    fun wordSpellingRepo() = wordSpellingRepository

    fun playlistDao() = dataBase.playlistDao()
}