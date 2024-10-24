package com.example.russian.mapper

import com.example.russian.enums.TaskTopicType
import com.example.russian.back.data.entity.MyTask
import com.example.russian.back.data.entity.PartOfTask

interface FormatToMyTaskMapperInterface {

    fun initialStringToWord(
        input: String,
        topic: TaskTopicType
    ): MyTask

    fun wordToPartOfTask(word: MyTask): List<PartOfTask>

    fun getDisplayableText(inputValue: String, topic: TaskTopicType): String
}