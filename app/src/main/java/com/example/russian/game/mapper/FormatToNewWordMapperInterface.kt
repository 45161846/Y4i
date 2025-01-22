package com.example.russian.game.mapper

import com.example.russian.main.TaskType
import com.example.russian.game.back.data.entity.MyTask
import com.example.russian.game.back.data.entity.PartOfTask

interface FormatToMyTaskMapperInterface {

    fun initialStringToWord(
        input: String,
        topic: TaskType
    ): MyTask

    fun wordToPartOfTask(word: MyTask): List<PartOfTask>

    fun getDisplayableText(inputValue: String, topic: TaskType): String
}