package com.example.russian.gameClasses

import com.example.russian.architecture.data.entity.Word
import com.example.russian.mainScreenPackage.repository.WordsLocalRepository
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