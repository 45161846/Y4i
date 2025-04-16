package com.example.russian.game.back.initialloading.impl

import android.content.res.AssetManager
import com.example.russian.game.back.data.entity.MyTask
import com.example.russian.game.back.data.entity.PartOfTask
import com.example.russian.game.back.data.entity.SpellingVariant
import com.example.russian.game.back.initialloading.arch.InitialLoadingFileReader
import com.example.russian.game.mapper.FormatToMyTaskMapper
import com.example.russian.game.mapper.FormatToMyTaskMapperInterface
import com.example.russian.main.TaskType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InitialLoadingFileReader(
    private val assertManager: AssetManager, private val checkFileName: String = "files_data.txt"
) : InitialLoadingFileReader {

    suspend fun readFile(fileName: String): List<String> {
        return withContext(Dispatchers.IO) {
            val stream = assertManager.open(fileName)

            val buffer = ByteArray(stream.available())

            stream.read(buffer)

            val l =
                String(buffer, charset("UTF-8")).split("(\\n*\\{[^\\}]*\\})".toRegex()).map { it ->
                        it.split("[;\\n+]".toRegex()).map { it.trim() }
                    }.flatten().filter { it.isNotEmpty() }

            List(l.size) {
                l[it].trim()
            }
        }
    }

    override suspend fun getAllWords(): List<MyTask> {
        val rows = readFile(checkFileName)

        val infos = childrenFilesInfo(rows)

        return getAllInitialWords(infos)
    }


    override suspend fun getAllPartOfTasksToDBWords(words: List<MyTask>): List<PartOfTask> {
        val mapper: FormatToMyTaskMapperInterface = FormatToMyTaskMapper
        return List(words.size) {
            mapper.wordToPartOfTask(words[it])
        }.flatten()
    }

    private suspend fun getAllInitialWords(infos: List<ChildFileInfo>): List<MyTask> {

        return List(infos.size) {
            getWordsByFileInfo(infos[it])
        }.flatten()

    }

    private fun childrenFilesInfo(values: List<String>): List<ChildFileInfo> {
        return List(values.size) {
            ChildFileInfo(values[it])
        }
    }

    private suspend fun getWordsByFileInfo(info: ChildFileInfo): List<MyTask> {
        val rows = readFile(info.childFileName)

        val mapper = FormatToMyTaskMapper

        return List(rows.size) {
            mapper.initialStringToWord(rows[it], info.topic)
        }
    }


    companion object {
        fun contextWord(inputValue: String): String {
            val parts = inputValue.split(";")

            if (parts.size < 2) return ""

            return parts.last()
        }

        //All words have has topic = 3
        suspend fun getAllSpellings(parts: List<PartOfTask>): List<SpellingVariant> {
            return parts.map { part ->
                part.value.split("|").map {
                    SpellingVariant(
                        partOfTaskId = part.id,
                        value = it.replace("*", ""),
                        correct = it.contains("*")
                    )
                }
            }.flatten()
        }
    }


    private class ChildFileInfo(
        value: String
    ) {

        val childFileName: String
        val topic: TaskType
        val rowsCount: Int


        init {
            val splitValues = value.split(";")
            childFileName = splitValues[0]
            topic = TaskType.valueOf(splitValues[1])
            rowsCount = Integer.parseInt(splitValues[2])
        }

    }
}