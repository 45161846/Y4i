package com.example.russian.main.mapper

import androidx.compose.ui.graphics.Color
import com.example.russian.R
import com.example.russian.architectured.TaskType
import com.example.russian.main.back.data.entity.Statistics
import com.example.russian.main.back.data.entity.TaskPartOfTask
import com.example.russian.main.enums.TaskTopicEnum
import com.example.russian.main.enums.TaskTopicType
import com.example.russian.main.tasks.TaskInterface
import com.example.russian.main.tasks.narecia.NarechiaTask
import com.example.russian.main.tasks.paronim.ParonimTask
import com.example.russian.main.tasks.ydarenia.Ydareni9Task
import com.example.russian.main.viewmodel.main.StatsParametersAPI
import com.example.russian.main.ui.state.StatCardUIState
import com.example.russian.main.ui.theme.LightGrey69
import com.example.russian.main.ui.theme.WhiteDD

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
            words: List<Statistics>,
            params: StatsParametersAPI
        ): List<StatCardUIState> = List(words.size) {
            val word = words[it]
            val wr = word.winRate()
            StatCardUIState(
                word.displayableText,
                word.winRate(),
                //todo
                //seleect image based on type
                R.drawable.ic_launcher_foreground,
                params = params,
                backgroundColor = LightGrey69,
                textColor = WhiteDD,
            )
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