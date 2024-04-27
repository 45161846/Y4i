package com.example.russian

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.os.Handler
import android.os.Looper

class MyViewModel:ViewModel() {

    var taskAllTexts = emptyList<String>()

    var taskText = MutableLiveData<String>("")
    var taskObject = MyTaskNarechia("")

    var right = MutableLiveData<Int>(0)
    var wrong = MutableLiveData<Int>(0)

    var summ = MutableLiveData<Int>(0)

    val typeOfButton = MutableLiveData<ButtonMode>(ButtonMode.TASK)

    private val updateDelay = 1000L

    fun updateTask(){
        var newTask = taskAllTexts.random()
        while (newTask == taskText.value!!){
            newTask = taskAllTexts.random()
        }
        taskText.value = newTask
        taskObject = MyTaskNarechia(newTask)
    }

    fun correct(){
        typeOfButton.value = ButtonMode.ANSWER_CORRECT

        Handler(Looper.getMainLooper()).postDelayed(
            {
                nextTask()
                right.value = right.value!! + 1
            }, updateDelay
        )
    }

    fun incorrect(){
        typeOfButton.value = ButtonMode.ANSWER_WRONG

        Handler(Looper.getMainLooper()).postDelayed(
            {
                nextTask()
                wrong.value = wrong.value!! + 1
            }, updateDelay
        )
    }

    private fun nextTask(){
        typeOfButton.value = ButtonMode.TASK
        updateTask()
    }

}