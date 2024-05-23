package com.example.russian.ViewModelPackage

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.russian.R
import com.example.russian.Word
import com.example.russian.WordDao
import com.example.russian.WordDataBase
import com.example.russian.WordsLocalRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class MyMainViewModelArch(
    application: Application,
    fileNamesAndTopics: Map<String, Int>
): AndroidViewModel(
    application
) {

    val repository = WordsLocalRepository()
    val loadingProcessesAmount = MutableLiveData<Int>(0)
    private var DB: WordDataBase = WordDataBase.getDatabase(application)
    var dao: WordDao = DB.wordDao()

    var sharedPreferences: SharedPreferences = application.getSharedPreferences(
        ContextCompat.getString(application, R.string.shared_preferences_key), Context.MODE_PRIVATE
    )
    var editorShP: SharedPreferences.Editor = sharedPreferences.edit()

    init {
        val firstStart = isFirstStart()
        if(firstStart) {
            executeFirstLoading(fileNamesAndTopics)
        }
    }

    fun executeFirstLoading(fileNames: Map<String, Int>){

        viewModelScope.launch {
            startLoading()
            val success = loadAllWordsFromInitialFiles(fileNames = fileNames)
            if(!success){
                throw Error("Couldn't execute first loading")
            }
            stopLoading()
        }
    }

    abstract fun markTopicAsAdded(fileName: String, topic: Int)

    abstract fun markAllFilesAddedToDB()

    abstract suspend fun addNewWordsToDB(newWords: List<Word>, topic: Int)

    abstract fun isFirstStart(): Boolean

    abstract suspend fun getAllWordsFromDBForRepository(): List<Word>

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

    private suspend fun loadAllWordsFromInitialFiles(fileNames: Map<String, Int>): Boolean{
        return withContext(Dispatchers.IO){
            fileNames.forEach{
                val l = loadWordsFromFile(it.key, it.value)
                addNewWordsToDB(newWords = l, topic = it.value)
                markTopicAsAdded(fileName = it.key, topic = it.value)
            }
            markAllFilesAddedToDB()
            true
        }
    }

    abstract fun loadWordsFromFile(fileName: String, topic: Int): List<Word>

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