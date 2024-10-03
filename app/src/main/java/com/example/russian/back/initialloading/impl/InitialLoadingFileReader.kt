package com.example.russian.back.initialloading.impl

import android.content.res.AssetManager
import com.example.russian.enums.TaskTopicType
import com.example.russian.back.data.entity.NewWord
import com.example.russian.back.data.entity.Spelling
import com.example.russian.back.initialloading.arch.InitialLoadingFileReader
import com.example.russian.mapper.FormatToNewWordMapper
import com.example.russian.mapper.FormatToNewWordMapperInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InitialLoadingFileReader(
    private val assertManager: AssetManager,
    private val checkFileName: String = "files_data.txt"
) : InitialLoadingFileReader {

    private suspend fun readFile(fileName: String): List<String> {
        return withContext(Dispatchers.IO) {
            val stream = assertManager.open(fileName)

            val buffer = ByteArray(stream.available())

            stream.read(buffer)

            val l = String(buffer, charset("UTF-8")).split("\n")
            List(l.size) {
                l[it].trim()
            }
        }
    }

    override suspend fun getAllWords(): List<NewWord> {
        val rows = readFile(checkFileName)

        val infos = childrenFilesInfo(rows)

        return getAllInitialWords(infos)
    }

    override suspend fun getAllSpellingsToDBWords(words: List<NewWord>): List<Spelling> {
        val mapper: FormatToNewWordMapperInterface = FormatToNewWordMapper
        return List(words.size) {
            mapper.wordToSpelling(words[it])
        }.flatten()
    }

    override fun contextWord(inputValue: String): String {
        val parts = inputValue.split(";")

        if (parts.size < 2) return ""

        return parts.last()
    }

    private suspend fun getAllInitialWords(infos: List<ChildFileInfo>): List<NewWord> {

        return List(infos.size) {
            getWordsByFileInfo(infos[it])
        }.flatten()

    }

    private fun childrenFilesInfo(values: List<String>): List<ChildFileInfo> {
        return List(values.size) {
            ChildFileInfo(values[it])
        }
    }

    private suspend fun getWordsByFileInfo(info: ChildFileInfo): List<NewWord> {
        val rows = readFile(info.childFileName)

        val mapper = FormatToNewWordMapper

        return List(rows.size) {
            mapper.initialStringToWord(rows[it], info.topic)
        }
    }


    private class ChildFileInfo(
        value: String
    ) {

        val childFileName: String
        val topic: TaskTopicType
        val rowsCount: Int


        init {
            val splitValues = value.split(";")
            childFileName = splitValues[0]
            topic = Integer.parseInt(splitValues[1])
            rowsCount = Integer.parseInt(splitValues[2])
        }

    }
}