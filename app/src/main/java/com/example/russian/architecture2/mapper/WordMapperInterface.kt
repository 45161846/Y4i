package com.example.russian.architecture2.mapper

import com.example.russian.architecture2.backend.data.entity.Statistics
import com.example.russian.architecture2.backend.data.entity.WordStatistics
import com.example.russian.architecture2.backend.data.entity.WordTaskSpelling
import com.example.russian.architecture2.ui.state.StatCardUIState
import com.example.russian.architecture2.viewmodel.main.StatsParametersAPI
import com.example.russian.tasks.TaskInterface

interface WordMapperInterface {

    fun wordToTask(word: WordTaskSpelling): TaskInterface

    fun wordListToCards(words: List<Statistics>, params: StatsParametersAPI): List<StatCardUIState>
}