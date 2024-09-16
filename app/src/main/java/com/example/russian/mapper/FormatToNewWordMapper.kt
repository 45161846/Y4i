package com.example.russian.mapper

import com.example.russian.enums.TaskTopicEnum
import com.example.russian.enums.TaskTopicType
import com.example.russian.back.data.entity.NewWord
import com.example.russian.back.data.entity.Spelling

class FormatToNewWordMapper {

    companion object : FormatToNewWordMapperInterface {
        override fun getDisplayableText(inputValue: String, topic: TaskTopicType): String {

            return when (topic) {
                TaskTopicEnum.NARECHIA -> narechiaText(inputValue)
                TaskTopicEnum.PARONIM -> paronimText(inputValue)
                TaskTopicEnum.YDARENIA -> ydarText(inputValue)
                else -> throw RuntimeException(
                    "Cannot get displayable text for word: $inputValue."
                            + "Of topic $topic"
                )
            }
        }

        override fun initialStringToWord(input: String, topic: TaskTopicType): NewWord {
            return NewWord(
                value = input,
                topic = topic
            )
        }

        override fun wordToSpelling(word: NewWord): List<Spelling> {

            return when (word.topic) {

                TaskTopicEnum.NARECHIA -> getAllNarechiaSpellings(word)

                TaskTopicEnum.PARONIM -> getAllParonimSpellings(word)

                TaskTopicEnum.YDARENIA -> getAllYdareniaSpellings(word)

                else -> {
                    throw IllegalArgumentException("Cannot get spellings for word: ${word.value}. Of topic: ${word.topic}")
                }
            }

        }

        private fun getAllNarechiaSpellings(word: NewWord): List<Spelling> {
            val spellingStrData = word.value.split(";")[0]

            val spellingsStr = spellingStrData.split("|")

            return List(spellingsStr.size) {
                Spelling(
                    wordId = word.id,
                    value = spellingsStr[it],
                    isCorrect = it == 0
                )
            }
        }

        private fun getAllParonimSpellings(word: NewWord): List<Spelling> {
            val parts = word.value.split(" - ")
            return List(parts.size) {
                Spelling(
                    wordId = word.id,
                    value = parts[it],
                    isCorrect = true
                )
            }
        }

        private fun getAllYdareniaSpellings(word: NewWord): List<Spelling> {
            return listOf(
                Spelling(
                    wordId = word.id,
                    value = word.value,
                    isCorrect = true
                )
            )
        }

        private fun narechiaText(input: String) = input.split("|")[0]

        private fun paronimText(input: String) = input
            .split(" - ")
            .joinToString(separator = " ") {
                it.split(" ")[0]
            }

        private fun ydarText(input: String) = input
    }
}