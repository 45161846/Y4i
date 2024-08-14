package com.example.russian.architecture2.application

import android.app.Application
import com.example.russian.architecture.data.olddata.db.WordPlaylistDatabase
import com.example.russian.architecture2.backend.data.db.NewWordPlaylistDatabase
import com.example.russian.architecture2.backend.initialloading.impl.InitialLoadingExecutor
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MyApplication : Application() {

    private lateinit var dataBase: NewWordPlaylistDatabase

    private lateinit var oldDataBase: WordPlaylistDatabase

    override fun onCreate() {
        super.onCreate()

        dataBase = NewWordPlaylistDatabase.getWordPlaylistDB(applicationContext)
        oldDataBase = WordPlaylistDatabase.getWordPlaylistDB(applicationContext)

        //initial loading if needed
        MainScope().launch {
            InitialLoadingExecutor(
                loadingDao(),
                assets,
            ).execute()
        }

    }

    fun gameDao() = dataBase.gameDao()

    fun loadingDao() = dataBase.loadingDao()

    fun statsDao() = dataBase.statsDao()
}