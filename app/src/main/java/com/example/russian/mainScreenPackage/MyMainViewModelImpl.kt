package com.example.russian.mainScreenPackage

import android.app.Application
import android.content.Context
import androidx.core.content.ContextCompat
import com.example.russian.MyEnumClasses.TaskTopic
import com.example.russian.R
import com.example.russian.database.Word
import com.example.russian.toolPackage.SharedPreferencesKeysHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyMainViewModelImpl(
    application: Application
): MyMainViewModelArch(
    application
) {

    override suspend fun addNewWordsToDB(newWords: List<Word>, topic: Int) {
        withContext(Dispatchers.IO) {
            dao.insert(newWords)
        }
    }

    override suspend fun getAllWordsFromDBForRepository(): List<Word> {
        return withContext(Dispatchers.IO){
            dao.getAllWords()
        }
    }

    override suspend fun checkAllFilesNumber(): Boolean {
        val filesData = readFilesData()
        val res = true
        filesData.forEach{
            if(!compareLinesOfTopicInDBAndFile(it)){
                val p = parseNameAndTopic(it)
                replaceWordsFromFileToDB(p.first, p.second)
            }
        }
        return res
    }

    private fun parseNameAndTopic(value: String): Pair<String, Int>{
        val l = value.split(";")
        return Pair(l[0], l[1].toInt())
    }

    override suspend fun readFilesData(): List<String> {

        val application = getApplication<Application>()

        val l = withContext(Dispatchers.IO){
            val f = application.assets.open(
                application.getString(R.string.files_data)
            )
            val buffer = ByteArray(f.available())
            f.read(buffer)
            f.close()
            String(buffer, charset("UTF-8")).split("/n")
        }

        return l
    }

    override suspend fun compareLinesOfTopicInDBAndFile(line: String): Boolean {
        val l = line.split(" - ")
        val topic = l[1].toInt()
        val count = l[2].toInt()
        return withContext(Dispatchers.IO){
            count == dao.countTopicTasks(topic)
        }
    }

    override fun getWordsFromFile(
        fileName: String,
        topic: Int
    ): List<Word> {
        val f = this.getApplication<Application>().assets.open(fileName)
        val buffer = ByteArray(f.available())
        f.read(buffer)
        f.close()
        val l = String(buffer, charset("UTF-8")).split("\n")

        return List(l.size, init = {
            Word(
                l[it],
                TaskTopic().NARECHI9,
                -1F
            )
        })
    }

}