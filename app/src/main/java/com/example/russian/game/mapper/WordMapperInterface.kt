package com.example.russian.game.mapper

import com.example.russian.game.back.data.entity.Statistics
import com.example.russian.game.back.data.entity.TaskPartOfTask
import com.example.russian.game.tasks.TaskInterface
import com.example.russian.main.settings.StatDisplaySetting
import com.example.russian.main.stats.comp.stats.TaskCardUiState

interface WordMapperInterface {

    fun wordToTask(word: TaskPartOfTask): TaskInterface

    fun wordListToCards(stats: List<Statistics>, params: StatDisplaySetting): List<TaskCardUiState>
}