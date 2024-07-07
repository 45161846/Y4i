package com.example.russian.architecture.initialloading

import android.content.res.AssetManager
import com.example.russian.architecture.data.entity.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InitialLoadingFileReaderImpl(
    override val assertManager: AssetManager,
    override val checkFileName: String = "files_data.txt"
) : InitialLoadingFileReader {


    override suspend fun readFile(fileName: String): List<String> {

        return withContext(Dispatchers.IO){
            val stream = assertManager.open(fileName)

            val buffer = ByteArray(stream.available())

            stream.read(buffer)

            val l = String(buffer, charset("UTF-8")).split("\n")
            List(l.size){
                l[it].trim()
            }
        }

    }

    override suspend fun getAllInitialWords(): List<Word> {
        val rows = readFile(checkFileName)

        val infos = childrenFilesInfo(rows)

        val lists = List(infos.size){
            getWordsByFileInfo(infos[it])
        }

        var initialCapacity = 0
        infos.forEach {info ->
            initialCapacity += info.rowsCount
        }

        val res = ArrayList<Word>(initialCapacity)
        lists.forEach {
            res.addAll(it)
        }

        return res.toList()
    }

    private fun childrenFilesInfo(values: List<String>): List<ChildFileInfo>{
        return List(values.size){
            ChildFileInfo(values[it])
        }
    }

    private suspend fun getWordsByFileInfo(info: ChildFileInfo): List<Word>{
        val rows = readFile(info.childFileName)

        return List(rows.size){
            Word(
                value = rows[it],
                topic = info.topicNumber,
                percentage = -1F
            )
        }
    }
}