package com.example.russian.architecture2.mapper

import com.example.russian.MyEnumClasses.TaskTopicEnum
import com.example.russian.MyEnumClasses.TaskTopicType
import com.example.russian.architecture2.backend.data.entity.NewWord
import com.example.russian.architecture2.backend.data.entity.Spelling

class FormatToNewWordMapper {

    companion object: FormatToNewWordMapperInterface{
        override fun getDisplayableText(inputValue: String): String {
            //TODO
            return ""
        }

        override fun initialStringToWord(input: String, topic: TaskTopicType): NewWord {
            return NewWord(
                input,
                topic
            )
        }

        override fun wordToSpelling(word: NewWord): List<Spelling> {

            return when(word.topic){

                TaskTopicEnum.NARECHIA -> getAllNarechiaSpellings(word)

                TaskTopicEnum.PARONIM -> getAllParonimSpellings(word)

                TaskTopicEnum.YDARENIA -> getAllYdareniaSpellings(word)

                else -> {throw IllegalArgumentException("Cannot get spellings for word: ${word.value}. Of topic: ${word.topic}")}
            }

        }

        private fun getAllNarechiaSpellings(word: NewWord): List<Spelling> {
            val spellingStrData = word.value.split(";")[0]

            val spellingsStr = spellingStrData.split("|")

            return List(spellingsStr.size){
                Spelling(
                    wordId = word.id,
                    value = spellingsStr[it],
                    isCorrect = it == 0
                )
            }
        }

        private fun getAllParonimSpellings(word: NewWord): List<Spelling> {
            val parts = word.value.split(" - ")
            return List(parts.size){
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
    }

}