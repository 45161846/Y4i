package com.example.russian.game.mapper

import androidx.compose.ui.graphics.Color
import com.example.russian.game.back.data.entity.MyTask
import com.example.russian.game.back.data.entity.Statistics
import com.example.russian.game.tasks.TaskInterface
import com.example.russian.game.tasks.one_to_many.MultipleChooseSingle
import com.example.russian.game.tasks.many_to_many.FindPairTask
import com.example.russian.game.tasks.ydarenia.Ydareni9Task
import com.example.russian.main.TaskType
import com.example.russian.main.settings.StatDisplaySetting
import com.example.russian.main.stats.comp.stats.TaskCardUiState
import com.example.russian.main.util.toCardUiState

class WordMapper {

    companion object : WordMapperInterface {
        override fun wordToTask(word: MyTask): TaskInterface {

            return when (val topic = word.topic) {
                TaskType.NARECHIA -> MultipleChooseSingle(word.value)
                TaskType.PARONIM -> FindPairTask(word.value)
                TaskType.YDARENIA -> Ydareni9Task(word.value)

                else -> throw unknownTopicError(topic)
            }
        }

        override fun wordListToCards(
            stats: List<Statistics>,
            params: StatDisplaySetting
        ): List<TaskCardUiState> = stats.map {
            it.toCardUiState(params)
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