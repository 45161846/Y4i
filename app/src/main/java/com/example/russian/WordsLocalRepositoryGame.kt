package com.example.russian

import com.example.russian.ViewModelPackage.GameSettings
import com.example.russian.toolPackage.DifferentTypeRandomizer

class WordsLocalRepositoryGame: WordsLocalRepository() {

    fun getNewRandomWord(gameSettings: GameSettings): Word? {
        return if(currentWords.isNotEmpty()){
            DifferentTypeRandomizer(
                gameSettings.sortedBy,
                currentWords
            ).random()
        }else{
            null
        }
    }

}