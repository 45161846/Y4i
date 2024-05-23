package com.example.russian.ViewModelPackage

import android.app.Activity
import android.app.Application
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.russian.MyEnumClasses.ButtonMode
import com.example.russian.R
import com.example.russian.Word
import com.example.russian.WordsLocalRepositoryGame
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class MyGameViewModelArch(
    application: Application,
    val topic: Int,
    val gameSettings: GameSettings
): AndroidViewModel(application) {

    val repository = WordsLocalRepositoryGame()
    val isLoadingInProcess = MutableLiveData<Boolean>(true)
    val currentWord = MutableLiveData<Word>()

    var right = 0
    var wrong = 0
    var typeOfButton = MutableLiveData(ButtonMode.TASK)

    val correctSoundMP: MediaPlayer = MediaPlayer.create(application, R.raw.correct_answer_sound)
    val incorrectSoundMP: MediaPlayer = MediaPlayer.create(application, R.raw.wrong_answer_sound)

    val vibrator: Vibrator = application.getSystemService(Activity.VIBRATOR_SERVICE) as Vibrator
    val vibrationDuration = 200L

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

    override fun onCleared() {
        correctSoundMP.release()
        incorrectSoundMP.release()
        repository.clear()
        super.onCleared()
    }
}