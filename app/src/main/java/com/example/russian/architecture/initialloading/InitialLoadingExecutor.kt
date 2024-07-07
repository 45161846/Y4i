package com.example.russian.architecture.initialloading

import android.content.res.AssetManager
import com.example.russian.architecture.data.db.WordPlaylistDatabase

interface InitialLoadingExecutor {

    val dataBase: WordPlaylistDatabase

    val assertManager: AssetManager

    val checkFileName: String

    suspend fun execute()
}