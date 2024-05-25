package com.example.russian.toolPackage.tree_search

import com.example.russian.database.Word
import com.example.russian.toolPackage.WordToTaskMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Searcher {

    private val tree = Tree()

    suspend fun setWords(words: List<Word>){
        withContext(Dispatchers.IO) {
            tree.insert(words)
        }
    }

    suspend fun search(prefix: String): List<Word>{

        return withContext(Dispatchers.IO){
            tree.getAllByPrefix(prefix)
        }

    }

    fun updateWord(w: Word){
        tree.insert(w)
    }

}