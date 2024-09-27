package com.example.russian.tool

import android.media.MediaPlayer

interface SoundAPI {

    fun playAnswerCorrect()
    fun playAnswerIncorrect()

    fun stopPLaying(mediaPlayer: MediaPlayer?)
}