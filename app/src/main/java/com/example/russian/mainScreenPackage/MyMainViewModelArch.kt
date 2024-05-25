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
import com.example.russian.toolPackage.WordToTaskMapper
import com.example.russian.toolPackage.tree_search.Searcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class MyMainViewModelArch(
    application: Application
): AndroidViewModel(
    application
) {

    val repository = WordsLocalMainScreenRepository()
    val loadingProcessesAmount = MutableLiveData(0)
    private var DB: WordDataBase = WordDataBase.getDatabase(application)
    var dao: WordDao = DB.wordDao()
    private lateinit var loadingJob: Job

    init {
        viewModelScope.launch {
            checkAllFilesNumber()
            setRepository()
        }
        loadingJob = viewModelScope.launch {  }
    }

    abstract suspend fun addNewWordsToDB(newWords: List<Word>, topic: Int)

    abstract suspend fun getAllWordsFromDBForRepository(): List<Word>

    /*
    compares amount of words added to db with numbers in files_data.txt
     */
    abstract suspend fun checkAllFilesNumber(): Boolean

    abstract suspend fun readFilesData(): List<String>

    abstract suspend fun compareLinesOfTopicInDBAndFile(line: String): Boolean

    fun getAllWordsForStats(): List<Word> = repository.currentWords.value!!

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

        if(repository.currentWords.value!!.isNotEmpty()){
            return
        }

        loadingJob = viewModelScope.launch{
            withContext(Dispatchers.IO){
                startLoading()
                val l = getAllWordsFromDBForRepository()
                val r = repository.setWords(l)
                stopLoading()
            }
        }
    }

    fun search(pref: String){
        loadingJob = viewModelScope.launch {
            startLoading()
            repository.leftAfterSearch(pref)
            stopLoading()
        }
    }

    fun cancelLoading(){
        if(loadingJob.isActive){
            loadingJob.cancel()
        }
    }

    fun clearRepository(){
        repository.clear()
    }

    fun resetCurrentWords(){
        loadingJob = viewModelScope.launch {
            startLoading()
            repository.resetFilters()
            stopLoading()
        }
    }

}