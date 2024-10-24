package com.example.russian.back.initialloading.arch

import com.example.russian.back.data.entity.MyTask
import com.example.russian.back.data.entity.PartOfTask

interface InitialLoadingFileReader {

    suspend fun getAllWords(): List<MyTask>

    suspend fun getAllPartOfTasksToDBWords(words: List<MyTask>): List<PartOfTask>

    fun contextWord(inputValue: String): String
}