package com.example.russian.application

import android.app.Application
import com.example.russian.back.data.db.MyTaskPlaylistDatabase
import com.example.russian.back.initialloading.impl.InitialLoadingExecutor
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MyApplication : Application() {

    private lateinit var dataBase: MyTaskPlaylistDatabase

    override fun onCreate() {
        super.onCreate()
        dataBase = MyTaskPlaylistDatabase.getWordPlaylistDB(applicationContext)
    }

    fun gameDao() = dataBase.gameDao()

    fun loadingDao() = dataBase.loadingDao()

    fun statsDao() = dataBase.statsDao()
}