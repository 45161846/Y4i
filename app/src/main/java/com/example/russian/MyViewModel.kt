package com.example.russian

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class MyViewModel(
    private val application: Application
) : AndroidViewModel(
    application
) {

    private val DB: WordDataBase = WordDataBase.getDatabase(application)
    private val dao: WordDao = DB.wordDao()

    private val sharedPreferences: SharedPreferences = application.getSharedPreferences(
        getString(application,R.string.shared_preferences_key), Context.MODE_PRIVATE
    )
    private val editorShP: SharedPreferences.Editor = sharedPreferences.edit()

    private var wordsAll = emptyList<Word>()

    var taskText = MutableLiveData(String())
    var taskObject = MyTaskNarechia(data = String())

    var right = MutableLiveData(0)
    var wrong = MutableLiveData(0)

    val typeOfButton = MutableLiveData(ButtonMode.TASK)

    private val updateDelay = 1000L



    init{
        viewModelScope.launch {
            if(sharedPreferences.getBoolean(
                    getString(application, R.string.shared_preferences_start), true
                )) {
                firstStartLoad()
            }else{
                regularStart()
            }
        }.invokeOnCompletion{
            updateTask()
        }
    }

    private fun updateTask() {
        var newWord = wordsAll.random()
        while (newWord.value == taskText.value!!) {
            newWord = wordsAll.random()
        }
        taskText.value = newWord.value
        taskObject = MyTaskNarechia(newWord)
    }

    fun correct() {
        typeOfButton.value = ButtonMode.ANSWER_CORRECT
        viewModelScope.launch {
            dao.update(
                taskObject.task_id,
                difference = 1
            )
        }

        Handler(Looper.getMainLooper()).postDelayed(
            {
                nextTask()
                right.value = right.value!! + 1
            }, updateDelay
        )
    }

    fun incorrect() {
        typeOfButton.value = ButtonMode.ANSWER_WRONG

        viewModelScope.launch {
            dao.update(
                taskObject.task_id,
                difference = 0
            )
        }

        Handler(Looper.getMainLooper()).postDelayed(
            {
                nextTask()
                wrong.value = wrong.value!! + 1
            }, updateDelay
        )
    }

    private fun nextTask() {
        updateTask()
        typeOfButton.value = ButtonMode.TASK
    }

    private fun firstStartLoad(){
        readFromFile()
        addNewWordsToDB()
        editorShP.putBoolean(
            getString(application, R.string.shared_preferences_start), false
        ).commit()
    }

    private suspend fun regularStart(){
        wordsAll = dao.getWordByTopic(TaskTopic().NARECHI9)
        if(wordsAll.isEmpty()){
            firstStartLoad()
        }
    }

    private fun readFromFile(){
        val f = application.assets.open("my_texts.txt")
        val buffer = ByteArray(f.available())
        f.read(buffer)
        f.close()
        val l = String(buffer, charset("UTF-8")).split("\n")

        wordsAll = List(l.size, init = {
            Word(
                v = l[it],
                taskTopic = TaskTopic().NARECHI9
            )
        })
    }

    private fun addNewWordsToDB(){
        viewModelScope.launch {
            dao.insert(wordsAll)
        }
    }

    fun calculatePercentage(){

    }

}