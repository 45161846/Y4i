package com.example.russian.architecture

import androidx.lifecycle.ViewModel
import com.example.russian.MyEnumClasses.MyFilterSettingsImpl
import com.example.russian.architecture.data.olddata.entity.Word
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class StatsScreenViewModel : ViewModel() {

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