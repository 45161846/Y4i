package com.example.russian.architecture.initialloading

import android.content.res.AssetManager
import com.example.russian.architecture.data.entity.Spelling
import com.example.russian.architecture.data.entity.Word
import com.example.russian.toolPackage.InitialFormatToWordMapperImpl
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

    override suspend fun getAllWords(): List<Word> {
        val rows = readFile(checkFileName)

        val infos = childrenFilesInfo(rows)

        return getAllInitialWords(infos)
    }

    override suspend fun getAllSpellingsToDBWords(words: List<Word>): List<Spelling> {
        val mapper = InitialFormatToWordMapperImpl()

        return List(words.size){
            mapper.wordToSpelling(words[it])
        }.flatten()
    }

    private suspend fun getAllInitialWords(infos: List<ChildFileInfo>): List<Word> {

        return List(infos.size){
            getWordsByFileInfo(infos[it])
        }.flatten()

    }

    private fun childrenFilesInfo(values: List<String>): List<ChildFileInfo>{
        return List(values.size){
            ChildFileInfo(values[it])
        }
    }

    private suspend fun getWordsByFileInfo(info: ChildFileInfo): List<Word>{
        val rows = readFile(info.childFileName)

        val mapper = InitialFormatToWordMapperImpl()

        return List(rows.size){
            mapper.initialStringToWord(rows[it], info.topicNumber)
        }
    }

}