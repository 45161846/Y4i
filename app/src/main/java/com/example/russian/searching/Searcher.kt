package com.example.russian.searching

import com.example.russian.MyEnumClasses.MyFilterSettings
import com.example.russian.MyEnumClasses.defaultFilterSettings
import com.example.russian.database.Word
import com.example.russian.searching.MyList
import com.example.russian.toolPackage.WordToTaskMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Searcher {

    private val myList = MyList()
    private var filterSettings = defaultFilterSettings()

    suspend fun setWords(words: List<Word>){
        myList.setWords(words)
    }

    suspend fun search(prefix: String): List<Word>{
        return myList.search(prefix)
    }

    suspend fun search(): List<Word>{
        return myList.search(filterSettings)
    }

    fun setPrefix(s: String){
        filterSettings.prefix = s
    }

    fun setFilterSettings(fs: MyFilterSettings){
        filterSettings = fs
    }

    fun getFilterSettings() = filterSettings

}