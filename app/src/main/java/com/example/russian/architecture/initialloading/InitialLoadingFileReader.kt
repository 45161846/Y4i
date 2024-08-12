package com.example.russian.architecture.initialloading

import android.content.res.AssetManager
import com.example.russian.architecture.data.entity.Spelling
import com.example.russian.architecture.data.entity.Word

interface InitialLoadingFileReader {

    val checkFileName: String

    val assertManager: AssetManager

    suspend fun readFile(fileName: String): List<String>

    suspend fun getAllWords(): List<Word>

    suspend fun getAllSpellingsToDBWords(words: List<Word>): List<Spelling>
}