package com.example.russian.toolPackage

import com.example.russian.architecture.data.entity.Spelling
import com.example.russian.architecture.data.entity.Word

interface InitialFormatToWordMapper {

    fun initialStringToWord(str: String, topicNumber: Int): Word

    fun wordToSpelling(word: Word): List<Spelling>
    fun getAllNarechiaSpellings(word: Word): List<Spelling>
    fun getAllParonimSpellings(word: Word): List<Spelling>
    fun getAllYdareniaSpellings(word: Word): List<Spelling>

}