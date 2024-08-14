package com.example.russian.architecture2.mapper

import com.example.russian.MyEnumClasses.TaskTopicType
import com.example.russian.architecture2.backend.data.entity.NewWord
import com.example.russian.architecture2.backend.data.entity.Spelling

interface FormatToNewWordMapperInterface {

    fun initialStringToWord(
        input: String,
        topic: TaskTopicType
    ): NewWord

    fun wordToSpelling(word: NewWord): List<Spelling>

    fun getDisplayableText(inputValue: String, topic: TaskTopicType): String
}