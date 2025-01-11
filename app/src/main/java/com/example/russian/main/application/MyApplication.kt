package com.example.russian.main.application

import android.app.Application
import com.example.russian.main.back.data.db.MyTaskPlaylistDatabase
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

class MyApplication : Application() {

    private lateinit var dataBase: MyTaskPlaylistDatabase

    override fun onCreate() {
        super.onCreate()
//        dataBase = MyTaskPlaylistDatabase.getWordPlaylistDB(applicationContext)
    }

    fun gameDao() = dataBase.gameDao()

    fun loadingDao() = dataBase.loadingDao()

    fun statsDao() = dataBase.statsDao()
}