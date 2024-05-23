package com.example.russian.ViewModelPackage

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.russian.MyEnumClasses.ButtonMode
import com.example.russian.MyTaskNarechia
import com.example.russian.R
import com.example.russian.MyEnumClasses.TaskTopic
import com.example.russian.Word
import com.example.russian.WordDao
import com.example.russian.WordDataBase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class MyViewModel(
    private val application: Application
) : AndroidViewModel(
    application
) {

    private var DB: WordDataBase = WordDataBase.getDatabase(application)
    private var dao: WordDao = DB.wordDao()

    private var fileName = String()
    private var topic = 0

    private val sharedPreferences: SharedPreferences = application.getSharedPreferences(
        getString(application, R.string.shared_preferences_key), Context.MODE_PRIVATE
    )
    private val editorShP: SharedPreferences.Editor = sharedPreferences.edit()

    private var wordsAll = emptyList<Word>()

    var taskText = MutableLiveData(String())
    var taskObject = MyTaskNarechia(data = String())

    var right = MutableLiveData(0)
    var wrong = MutableLiveData(0)

    val typeOfButton = MutableLiveData(ButtonMode.TASK)

    private val updateDelay = 1000L

    var loading = MutableLiveData(false)

    init {
        start(TaskTopic().NARECHI9, getString(application, R.string.narechia_file))
    }
    private fun updateTask() {
        var newWord = wordsAll.random()
        while (newWord.value == taskText.value!!) {
            newWord = wordsAll.random()
        }
        /*
        в первый раз, после загрузки wordsAll оказывается заполнен словами с нулевыми айдишниками,
        поэтому потом я не могу их вытянуть, по этому айди,  тк айди присваивается только БД, и чтобы
        их получить, надо вызывать опять все слова из БД.
         */

        val w = newWord
        viewModelScope.launch {
            newWord = dao.getWordById(newWord.id)
            taskText.value = newWord!!.value
            taskObject = MyTaskNarechia(newWord!!)
        }

    }
    fun start(topic: Int,fileName: String){
        this.topic = topic
        this.fileName = fileName
        viewModelScope.launch {
            async {
                getFromDB(topic, fileName)
            }.await()
            async {
                updateTask()
            }.await()
        }
    }

    private suspend fun getFromDB(topic: Int = this.topic, fileName: String = this.fileName){
        if(sharedPreferences.getBoolean(
                    getString(application, R.string.shared_preferences_start), true
                )) {
                firstStartLoad(fileName)
            }else{
                regularStart()
            }
    }
    private suspend fun firstStartLoad(fileName: String = this.fileName){
        viewModelScope.launch {
            readFromFile(fileName)
        }.invokeOnCompletion {
            editorShP.putBoolean(
                getString(application, R.string.shared_preferences_start), false
            ).commit()
        }

    }
    private suspend fun readFromFile(fileName: String = this.fileName){
        viewModelScope.launch {
            val f = application.assets.open(fileName)
            val buffer = ByteArray(f.available())
            f.read(buffer)
            f.close()
            val l = String(buffer, charset("UTF-8")).split("\n")

            wordsAll = List(l.size, init = {
                Word(
                    l[it],
                    TaskTopic().NARECHI9,
                    -1F
                )
            })
            async {
                addNewWordsToDB(wordsAll)
            }.await()
            wordsAll = async {
                dao.getWordByTopic(TaskTopic().NARECHI9)
            }.await()
            Log.d("mTag", wordsAll.toString())
        }

    }

    private suspend fun regularStart(){
        viewModelScope.launch {
            async {
                wordsAll = dao.getWordByTopic(TaskTopic().NARECHI9)
            }.await()
            if(wordsAll.isEmpty()){
                firstStartLoad()
            }
        }
    }

    private fun addNewWordsToDB(words: List<Word>){
        viewModelScope.launch {
            dao.insert(words)
        }
    }

    private fun nextTask() {
        viewModelScope.launch {
            async {
                updateTask()
            }.await()
            typeOfButton.value = ButtonMode.TASK
        }

    }

    fun correct() {
        typeOfButton.value = ButtonMode.ANSWER_CORRECT
        viewModelScope.launch {
            dao.update(
                taskObject.task_id,
                difference = 1,
                percent = ((taskObject.correctAnswersAmount + 1).toFloat() / (taskObject.attemptAmount + 1).toFloat())
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
                difference = 0,
                percent = (taskObject.correctAnswersAmount.toFloat() / (taskObject.attemptAmount + 1).toFloat())
            )
        }

        Handler(Looper.getMainLooper()).postDelayed(
            {
                nextTask()
                wrong.value = wrong.value!! + 1
            }, updateDelay
        )
    }

    fun loadAllWords(){
        viewModelScope.launch {
            loading.value = true
            loading.value = async {
                wordsAll = dao.getAllWords()
                false
            }.await()
        }
    }

    fun getAllWords():List<Word>{
        return wordsAll
    }

    fun clearWords(){
        wordsAll = emptyList()
    }

}