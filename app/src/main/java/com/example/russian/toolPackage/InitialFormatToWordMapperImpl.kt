package com.example.russian.toolPackage

import com.example.russian.MyEnumClasses.TaskTopicEnum
import com.example.russian.architecture.data.olddata.entity.Spelling
import com.example.russian.architecture.data.olddata.entity.Word

class InitialFormatToWordMapperImpl: InitialFormatToWordMapper {

    override fun initialStringToWord(str: String, topicNumber: Int): Word {
        return Word(
            str,
            topicNumber,
            -1F
        )
    }

    override fun wordToSpelling(word: Word): List<Spelling> {

        return when(word.topic){

            TaskTopicEnum.NARECHIA -> getAllNarechiaSpellings(word)

            TaskTopicEnum.PARONIM -> getAllParonimSpellings(word)

            TaskTopicEnum.YDARENIA -> getAllYdareniaSpellings(word)

            else -> {throw IllegalArgumentException("Cannot get spellings for word: ${word.value}. Of topic: ${word.topic}")}
        }

    }

    override fun getAllNarechiaSpellings(word: Word): List<Spelling> {
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

    override fun getAllParonimSpellings(word: Word): List<Spelling>{
        val parts = word.value.split(" - ")
        return List(parts.size){
            Spelling(
                wordId = word.id,
                value = parts[it],
                isCorrect = true
            )
        }
    }

    override fun getAllYdareniaSpellings(word: Word): List<Spelling>{
        return listOf(
            Spelling(
                wordId = word.id,
                value = word.value,
                isCorrect = true
            )
        )
    }
}