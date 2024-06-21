package com.example.russian.mainScreenPackage

import androidx.lifecycle.MutableLiveData
import com.example.russian.database.Word
import com.example.russian.searching.Searcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WordsLocalMainScreenRepository(words: List<Word> = emptyList()):WordsRepositoryInterface {

    var allWords:  ArrayList<Word> = ArrayList(words)
    val searcher = Searcher()
    var currentWords: MutableLiveData<ArrayList<Word>> = MutableLiveData(arrayListOf())

    var hasWordsAfterSearch = MutableLiveData(true)

    override suspend fun setWords(words: List<Word>): Boolean{
        return withContext(Dispatchers.Main){
            searcher.setWords(words)
            allWords = ArrayList(searcher.search(String()))
            currentWords.value = allWords
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
        val result = ArrayList(searcher.search(prefix))
        currentWords.value = result

        hasWordsAfterSearch.value = result.isNotEmpty()

    }

    fun resetFilters(){
        currentWords.value = allWords
        hasWordsAfterSearch.value = true
    }

    fun getCurrentWords() = currentWords.value ?: emptyList<Word>()

}