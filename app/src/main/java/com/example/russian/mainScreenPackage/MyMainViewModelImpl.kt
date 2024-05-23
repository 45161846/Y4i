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
    application: Application,
    fileNamesAndTopics: Map<String, Int>
): MyMainViewModelArch(
    application,
    fileNamesAndTopics
) {

    override fun markTopicAsAdded(fileName: String, topic: Int) {
        editorShP
            .putBoolean(SharedPreferencesKeysHolder().topicShPKey(topic), true)
            .commit()
    }

    override fun markAllFilesAddedToDB() {
        editorShP
            .putBoolean(SharedPreferencesKeysHolder().firstStartKey(), false)
            .commit()
    }

    override suspend fun addNewWordsToDB(newWords: List<Word>, topic: Int) {
        withContext(Dispatchers.IO) {
            dao.insert(newWords)
        }
    }

    override fun isFirstStart(): Boolean {
        sharedPreferences = getApplication<Application>().getSharedPreferences(
            ContextCompat.getString(getApplication(), R.string.shared_preferences_key), Context.MODE_PRIVATE
        )
        editorShP = sharedPreferences.edit()
        return sharedPreferences.getBoolean(SharedPreferencesKeysHolder().firstStartKey(), true)
    }

    override suspend fun getAllWordsFromDBForRepository(): List<Word> {
        return withContext(Dispatchers.IO){
            dao.getAllWords()
        }
    }

    override fun loadWordsFromFile(
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