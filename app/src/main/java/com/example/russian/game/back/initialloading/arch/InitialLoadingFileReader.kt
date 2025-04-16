package com.example.russian.game.back.initialloading.arch

import com.example.russian.game.back.data.entity.MyTask
import com.example.russian.game.back.data.entity.PartOfTask

interface InitialLoadingFileReader {

    suspend fun getAllWords(): List<MyTask>

    suspend fun getAllPartOfTasksToDBWords(words: List<MyTask>): List<PartOfTask>
}