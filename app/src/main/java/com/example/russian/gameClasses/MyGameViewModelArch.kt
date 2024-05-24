package com.example.russian.gameClasses

import android.app.Activity
import android.app.Application
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.russian.MyEnumClasses.ButtonMode
import com.example.russian.MyEnumClasses.TaskTopic
import com.example.russian.R
import com.example.russian.database.Word
import com.example.russian.toolPackage.TaskInterface
import com.example.russian.toolPackage.WordToTaskMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class MyGameViewModelArch(
    application: Application,
    val topic: Int,
    private val gameSettings: GameSettings
): AndroidViewModel(application) {

    val repository = WordsLocalRepositoryGame()
    val isLoadingInProcess = MutableLiveData(true)
    val currentWord = MutableLiveData<Word>()
    var currentTask: TaskInterface? = null

    var right = 0
    var wrong = 0
    var typeOfButton = MutableLiveData(ButtonMode.TASK)

    private val correctSoundMP: MediaPlayer = MediaPlayer.create(application, R.raw.correct_answer_sound)
    private val incorrectSoundMP: MediaPlayer = MediaPlayer.create(application, R.raw.wrong_answer_sound)

    private val vibrator: Vibrator = application.getSystemService(Activity.VIBRATOR_SERVICE) as Vibrator
    private val vibrationDuration = 200L

    init {
        viewModelScope.launch{
            val words = loadRepository()
            repository.setWords(words)
            stopLoading()
            assign()
        }

    }

    private fun assign(){

        currentWord.value = repository.getNewRandomWord(gameSettings)
        typeOfButton.value = ButtonMode.TASK
        isLoadingInProcess.value = false

    }

    private suspend fun loadRepository(): List<Word>{
        return withContext(Dispatchers.IO){
            startLoading()
            val receivedWords = getWordsFromDB(topic)
            receivedWords
        }

    }

    fun correctAnswer(){
        correctSoundMP.start()
        right += 1
        typeOfButton.value = ButtonMode.ANSWER_CORRECT
        viewModelScope.launch(Dispatchers.IO) {
            changeWordAfterAnswer(currentWord.value, true)
        }

        nextTask()
    }

    fun incorrectAnswer(){
        incorrectSoundMP.start()
        myVibrate()
        wrong += 1
        typeOfButton.value = ButtonMode.ANSWER_WRONG
        viewModelScope.launch(Dispatchers.IO) {
            changeWordAfterAnswer(currentWord.value, false)
        }
        nextTask()
    }

    private fun myVibrate(){
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(vibrationDuration, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(vibrationDuration)
        }
    }

    private fun nextTask(){

        viewModelScope.launch {
            delay(gameSettings.delayBetweenAnswerAndNextTask)
            assign()
        }

    }

    abstract suspend fun updateWordInDB(value: Word?)

    abstract suspend fun changeWordAfterAnswer(value: Word?, isAnswerCorrect: Boolean)

    abstract suspend fun getWordsFromDB(topic: Int): List<Word>


    private suspend fun startLoading(){
        withContext(Dispatchers.Main){
            isLoadingInProcess.value = true
        }
    }
    private suspend fun stopLoading(){
        withContext(Dispatchers.Main){
            isLoadingInProcess.value = false
        }
    }

    fun createTask(): TaskInterface{
        currentTask = when(topic){
            TaskTopic().NARECHI9 -> WordToTaskMapper().wordToNarechieTask(currentWord.value!!)
            TaskTopic().PARONIM -> WordToTaskMapper().toContextTask(currentWord.value!!.value)
            else -> throw Error("Unexpected topic of word")
        }
        return currentTask!!
    }

    override fun onCleared() {
        correctSoundMP.release()
        incorrectSoundMP.release()
        repository.clear()
        super.onCleared()
    }
}