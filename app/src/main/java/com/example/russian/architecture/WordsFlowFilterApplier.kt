package com.example.russian.architecture

import com.example.russian.MyEnumClasses.MyFilterSettings
import com.example.russian.architecture.data.entity.Word
import kotlinx.coroutines.flow.Flow

interface WordsFlowFilterApplier{
    val filterSettings: MyFilterSettings

    fun applyFilters(flow: Flow<List<Word>>, filters: MyFilterSettings = filterSettings) : Flow<List<Word>>

}