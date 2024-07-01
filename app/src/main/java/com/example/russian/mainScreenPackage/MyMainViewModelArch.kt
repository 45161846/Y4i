package com.example.russian.mainScreenPackage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.viewModelScope
import com.example.russian.MyEnumClasses.MyFilterSettings
import com.example.russian.MyEnumClasses.defaultFilterSettings
import com.example.russian.database.Word
import com.example.russian.database.WordDao
import com.example.russian.database.WordDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class MyMainViewModelArch(
    application: Application
): AndroidViewModel(
    application
) {

    val repository = WordsLocalMainScreenRepository()
    val loadingInProcess = MutableLiveData(false)
    private var DB: WordDataBase = WordDataBase.getDatabase(application)
    var dao: WordDao = DB.wordDao()
    private var loadingJob: Job

    init {
        loadingJob = viewModelScope.launch(Dispatchers.IO) {
            checkAllFilesNumber()
            withContext(Dispatchers.Main){
                setRepository()
            }
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

    fun getAllWordsForStats(): List<Word> = repository.currentWords.value!!

    private  fun startLoading(){
        repository.isLoadingInProcess = true
    }
    private  fun stopLoading(){
        repository.isLoadingInProcess = false
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
        startLoading()
        loadingJob = viewModelScope.launch(Dispatchers.IO){
            repository.setWords(getAllWordsFromDBForRepository())
        }
        stopLoading()
    }

    fun search(pref: String){

        startLoading()

        loadingJob = viewModelScope.launch(Dispatchers.Main) {
            repository.leftAfterSearch(pref)
        }

        stopLoading()
    }

    fun search(){
        startLoading()
        loadingJob = viewModelScope.launch(Dispatchers.Main) {
            repository.leftAfterSearch()
        }
        stopLoading()
    }

    fun clearRepository(){
        repository.clear()
    }

    fun setFilterSettings(fs: MyFilterSettings){
        repository.setFilterSettings(fs)
    }

    fun getFilterSettings() = repository.getFilterSettings()
}