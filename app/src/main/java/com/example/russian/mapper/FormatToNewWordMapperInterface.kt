package com.example.russian.mapper

import com.example.russian.enums.TaskTopicType
import com.example.russian.back.data.entity.NewWord
import com.example.russian.back.data.entity.Spelling

interface FormatToNewWordMapperInterface {

    fun initialStringToWord(
        input: String,
        topic: TaskTopicType
    ): NewWord

    fun wordToSpelling(word: NewWord): List<Spelling>

    fun getDisplayableText(inputValue: String, topic: TaskTopicType): String
}