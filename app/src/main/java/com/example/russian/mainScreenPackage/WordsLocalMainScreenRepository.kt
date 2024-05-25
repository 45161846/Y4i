package com.example.russian.mainScreenPackage

import androidx.lifecycle.MutableLiveData
import com.example.russian.database.Word
import com.example.russian.toolPackage.tree_search.Searcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WordsLocalMainScreenRepository(words: List<Word> = emptyList()):WordsRepositoryInterface {

    var allWords:  ArrayList<Word> = ArrayList(words)
    val searcher = Searcher()
    var currentWords: MutableLiveData<ArrayList<Word>> = MutableLiveData(arrayListOf())

    override suspend fun setWords(words: List<Word>): Boolean{
        return withContext(Dispatchers.Main){
            allWords = ArrayList(words)
            currentWords.value = allWords
            searcher.setWords(words)
            true
        }
    }

    override fun updateWord(w: Word) {
        for(index in currentWords.value!!.indices){
            if(currentWords.value!![index].id == w.id){
                currentWords.value!![index] = w
                allWords[index] = w
            }
        }
    }

    override fun clear() {
        currentWords.value = arrayListOf()
    }

    suspend fun leftAfterSearch(prefix: String){
        currentWords.value = ArrayList(searcher.search(prefix))
    }

    fun resetFilters(){
        currentWords.value = allWords
    }

}