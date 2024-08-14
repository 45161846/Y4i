package com.example.russian.architecture2.mapper

import com.example.russian.architecture2.backend.data.entity.WordTaskSpelling
import com.example.russian.tasks.TaskInterface

interface WordMapperInterface {

    fun wordToTask(word: WordTaskSpelling): TaskInterface

}