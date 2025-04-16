package com.example.russian.main.data

import android.content.Context
import android.widget.Toast
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ToastHandler @Inject constructor(
    private val context: Context
) {

    fun createToast(text: String, short: Boolean){
        Toast.makeText(context, text, if(short) Toast.LENGTH_SHORT else Toast.LENGTH_LONG).show()
    }

}