package com.example.russian.mainScreenPackage

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.russian.R
import com.example.russian.database.Word
import com.example.russian.database.WordDao
import com.example.russian.database.WordDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class MyMainViewModelArch(
    application: Application
): AndroidViewModel(
    application
) {

    val repository = WordsLocalRepository()
    val loadingProcessesAmount = MutableLiveData(0)
    private var DB: WordDataBase = WordDataBase.getDatabase(application)
    var dao: WordDao = DB.wordDao()

    init {
        viewModelScope.launch {
            checkAllFilesNumber()
        }
    }

    abstract suspend fun addNewWordsToDB(newWords: List<Word>, topic: Int)

    abstract suspend fun getAllWordsFromDBForRepository(): List<Word>

    /*
    compares amount of words added to db with numbers in files_data.txt
     */
    abstract suspend fun checkAllFilesNumber(): Boolean

    abstract suspend fun readFilesData(): List<String>

    abstract suspend fun compareLinesOfTopicInDBAndFile(line: String): Boolean

    fun getAllWordsForStats(): List<Word> = repository.currentWords

    private suspend fun startLoading(){
        withContext(Dispatchers.Main){
            loadingProcessesAmount.value = loadingProcessesAmount.value!! + 1
        }
    }
    private suspend fun stopLoading(){

        withContext(Dispatchers.Main){
            loadingProcessesAmount.value = loadingProcessesAmount.value!! - 1
        }

    }

    suspend fun replaceWordsFromFileToDB(file_name: String, topic: Int){
        val ids = getIdByTopic(topic)
        removeWordsByIds(ids)
        val l = getWordsFromFile(file_name, topic)
        addNewWordsToDB(newWords = l, topic = topic)
    }

    private suspend fun getIdByTopic(topic: Int): List<Int>{
        return dao.getIdByTopic(topic)
    }
    private suspend fun removeWordsByIds(idList: List<Int>){
        dao.removeWords(idList)
    }

    abstract fun getWordsFromFile(fileName: String, topic: Int): List<Word>

    fun setRepository(){

        if(repository.currentWords.isNotEmpty()){
            return
        }

        viewModelScope.launch {
            startLoading()
            val l = getAllWordsFromDBForRepository()
            val r = repository.setWords(l)
            stopLoading()
        }
    }

    fun clearRepository(){
        repository.clear()
    }

}