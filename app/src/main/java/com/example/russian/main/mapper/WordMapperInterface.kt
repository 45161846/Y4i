package com.example.russian.main.mapper

import com.example.russian.main.back.data.entity.Statistics
import com.example.russian.main.back.data.entity.TaskPartOfTask
import com.example.russian.main.tasks.TaskInterface
import com.example.russian.main.ui.state.StatCardUIState
import com.example.russian.main.viewmodel.main.StatsParametersAPI

interface WordMapperInterface {

    fun wordToTask(word: TaskPartOfTask): TaskInterface

    fun wordListToCards(words: List<Statistics>, params: StatsParametersAPI): List<StatCardUIState>
}