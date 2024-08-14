package com.example.russian.architecture

import androidx.lifecycle.viewModelScope
import com.example.russian.MyEnumClasses.FilterApplier
import com.example.russian.architecture.data.olddata.entity.Word
import com.example.russian.architecture.repository.LocalWordRepository
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StatsScreenViewModelImpl:  StatsScreenViewModel(){

    private lateinit var repository: LocalWordRepository

    fun setArguments(repository: LocalWordRepository){
        this.repository = repository
        getWordsOnScreen()
    }

    override fun getWordsOnScreen() {

        viewModelScope.launch {
            repository.dbWordFlow.collect{
                repository.updateCache(it)
                updateWordsOnScreen(FilterApplier.filterList(it, filter))
            }
        }

    }

    override fun changeFilter(pref: String) {
        filter.changePrefix(pref)
    }

    override fun addWord(word: Word) {
        viewModelScope.launch {
            repository.addWord(word)
        }
    }

    override fun addWords(words: List<Word>) {
        viewModelScope.launch {
            repository.addWords(words)
        }
    }

    override fun updateWordsOnScreen(list: List<Word>) {
        _wordsOnScreen.update { list }
    }

    override fun applyFilter() {
        viewModelScope.launch {
            _wordsOnScreen.update {
                FilterApplier.filterList(repository.cachedWords, filter)
            }
        }
    }
}