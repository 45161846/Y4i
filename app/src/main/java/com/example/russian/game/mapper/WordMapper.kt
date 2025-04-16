package com.example.russian.game.mapper

import androidx.compose.ui.graphics.Color
import com.example.russian.game.back.data.entity.Statistics
import com.example.russian.game.back.data.entity.TaskPartOfTask
import com.example.russian.game.tasks.TaskInterface
import com.example.russian.game.tasks.narecia.NarechiaTask
import com.example.russian.game.tasks.paronim.ParonimTask
import com.example.russian.game.tasks.ydarenia.Ydareni9Task
import com.example.russian.main.TaskType
import com.example.russian.main.settings.StatDisplaySetting
import com.example.russian.main.stats.comp.stats.TaskCardUiState
import com.example.russian.main.util.toCardUiState

class WordMapper {

    companion object : WordMapperInterface {
        override fun wordToTask(word: TaskPartOfTask): TaskInterface {

            return when (val topic = word.taskNoPartOfTask.word.topic) {
                TaskType.NARECHIA -> createNarechiaTask(word)
                TaskType.PARONIM -> createParonimTask(word)
                TaskType.YDARENIA -> createYdarTask(word)

                else -> throw unknownTopicError(topic)
            }
        }

        override fun wordListToCards(
            stats: List<Statistics>,
            params: StatDisplaySetting
        ): List<TaskCardUiState> = stats.map {
            it.toCardUiState(params)
        }

        private fun createYdarTask(word: TaskPartOfTask): TaskInterface {
            return Ydareni9Task(word.taskNoPartOfTask.word.value)
        }

        private fun createParonimTask(word: TaskPartOfTask): TaskInterface {
            return ParonimTask(word.PartOfTasks)
        }

        private fun createNarechiaTask(word: TaskPartOfTask): TaskInterface {
            return NarechiaTask(word.PartOfTasks, word.taskNoPartOfTask.taskData.contextText)
        }

        private fun unknownTopicError(topic: TaskType) =
            IllegalArgumentException("Cannot resolve type of topic: $topic")


        private fun calculateColor(winRate: Double): Color {
            if (winRate < 0) {
                return Color(152, 152, 152)
            }
            return Color(((1.0 - winRate) * 2).toFloat(), (winRate * 2).toFloat(), 0F, alpha = 1F)
        }
    }

}