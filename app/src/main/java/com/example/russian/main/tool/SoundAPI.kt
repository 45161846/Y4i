package com.example.russian.main.tool

import android.media.MediaPlayer

interface SoundAPI {

    fun playAnswerCorrect()
    fun playAnswerIncorrect()

    fun stopPLaying(mediaPlayer: MediaPlayer?)
}