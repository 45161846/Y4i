package com.example.russian.main.mapper

import com.example.russian.main.back.data.entity.MyTask
import com.example.russian.main.back.data.entity.PartOfTask
import com.example.russian.main.enums.TaskTopicType

interface FormatToMyTaskMapperInterface {

    fun initialStringToWord(
        input: String,
        topic: TaskTopicType
    ): MyTask

    fun wordToPartOfTask(word: MyTask): List<PartOfTask>

    fun getDisplayableText(inputValue: String, topic: TaskTopicType): String
}