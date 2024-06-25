package com.example.russian.searching

import com.example.russian.MyEnumClasses.MyFilterSettings
import com.example.russian.database.Word
import com.example.russian.toolPackage.DifferentTypeSort
import com.example.russian.toolPackage.WordToTaskMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyList{

    private var value: List<Word> = emptyList()

    suspend fun setWords(words: List<Word>){
        val mapper = WordToTaskMapper()
        withContext(Dispatchers.Default) {
            value = words.sortedBy {
                mapper.getDisplayableText(it).lowercase()
            }
        }
    }

    suspend fun search(prefix: String): List<Word>{
        return withContext(Dispatchers.Default){
            val ans = getByPref(prefix)
            ans.sortedBy {
                it.topic
            }
        }

    }

    suspend fun search(fs: MyFilterSettings): List<Word>{
        return withContext(Dispatchers.Default){
            var ans = getByPref(fs.prefix).filter {
                it.topic in fs.topics
            }
            if(!fs.showUnanswered){
                ans = ans.filter {
                    it.attempts > 0
                }
            }
            DifferentTypeSort(array = ans, sortType = fs.typeOfSort).sort()
        }

    }

    private suspend fun getByPref(prefix: String): List<Word>{
        val start: Int = findFirstPref(prefix) ?: -1
        val end: Int = findLastPref(prefix) ?: -1
        return if(start >= 0 && end >= 0){
            withContext(Dispatchers.Default){
                value.slice(start..end)
            }
        }else{
            emptyList()
        }
    }

    private suspend fun findFirstPref(prefix: String): Int?{

        return withContext(Dispatchers.Default){

            val mapper = WordToTaskMapper()
            var first: Int? = null
            var low = 0
            var high = value.size - 1
            var mid: Int
            var current: String

            while (low <= high) {
                mid = low + (high - low) / 2
                current = mapper.getDisplayableText(value[mid]).lowercase()
                if(current.startsWith(prefix)){
                    first = mid
                    high = mid - 1
                }
                else if (current < prefix) {
                    low = mid + 1
                } else {
                    high = mid - 1
                }
            }
            first
        }
    }

    private suspend fun findLastPref(prefix: String): Int?{
        return withContext(Dispatchers.Default){

            val mapper = WordToTaskMapper()
            var last: Int? = null
            var low = 0
            var high = value.size - 1
            var mid: Int
            var current: String

            while (low <= high) {
                mid = low + (high - low) / 2
                current = mapper.getDisplayableText(value[mid]).lowercase()
                if(current.startsWith(prefix)){
                    last = mid
                    low = mid + 1
                }
                else if (current < prefix) {
                    low = mid + 1
                } else {
                    high = mid - 1
                }
            }
            last
        }
    }

}