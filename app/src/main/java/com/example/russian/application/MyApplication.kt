package com.example.russian.application

import android.app.Application
import com.example.russian.back.data.db.NewWordPlaylistDatabase
import com.example.russian.back.initialloading.impl.InitialLoadingExecutor
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MyApplication : Application() {

    private lateinit var dataBase: NewWordPlaylistDatabase


    override fun onCreate() {
        super.onCreate()

        dataBase = NewWordPlaylistDatabase.getWordPlaylistDB(applicationContext)

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