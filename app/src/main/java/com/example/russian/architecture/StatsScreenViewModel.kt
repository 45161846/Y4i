package com.example.russian.architecture

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.russian.MyEnumClasses.MyFilterSettings
import com.example.russian.MyEnumClasses.MyFilterSettingsImpl
import com.example.russian.MyEnumClasses.defaultFilterSettings
import com.example.russian.architecture.repository.LocalWordRepository
import com.example.russian.architecture.data.entity.Word
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class StatsScreenViewModel(
    application: Application,
    val repository: LocalWordRepository
) : AndroidViewModel(application) {

    val _wordsOnScreen = MutableStateFlow(emptyList<Word>())

    val filter = MyFilterSettingsImpl.defaultFilterSettings()

    val wordsOnScreen = _wordsOnScreen.asStateFlow()


    abstract fun getWordsOnScreen() //initial function

    abstract fun changeFilter(pref: String)

    abstract fun addWord(word: Word)

    abstract fun addWords(words: List<Word>)

    abstract fun updateWordsOnScreen(list: List<Word>)

    abstract fun applyFilter()
}