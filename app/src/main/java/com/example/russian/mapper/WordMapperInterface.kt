package com.example.russian.mapper

import com.example.russian.back.data.entity.Statistics
import com.example.russian.back.data.entity.TaskPartOfTask
import com.example.russian.tasks.TaskInterface
import com.example.russian.ui.state.StatCardUIState
import com.example.russian.viewmodel.main.StatsParametersAPI

interface WordMapperInterface {

    fun wordToTask(word: TaskPartOfTask): TaskInterface

    fun wordListToCards(words: List<Statistics>, params: StatsParametersAPI): List<StatCardUIState>
}