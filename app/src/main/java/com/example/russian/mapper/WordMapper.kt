package com.example.russian.mapper

import androidx.compose.ui.graphics.Color
import com.example.russian.R
import com.example.russian.back.data.entity.Statistics
import com.example.russian.back.data.entity.WordTaskSpelling
import com.example.russian.enums.TaskTopicEnum
import com.example.russian.enums.TaskTopicType
import com.example.russian.tasks.TaskInterface
import com.example.russian.tasks.narecia.NarechiaTask
import com.example.russian.tasks.paronim.ParonimTask
import com.example.russian.tasks.ydarenia.Ydareni9Task
import com.example.russian.ui.state.StatCardUIState
import com.example.russian.ui.theme.OnSecondary2
import com.example.russian.ui.theme.ThirdBackground
import com.example.russian.viewmodel.main.StatsParametersAPI

class WordMapper {

    companion object : WordMapperInterface {
        override fun wordToTask(word: WordTaskSpelling): TaskInterface {

            return when (val topic = word.wordTask.word.topic) {
                TaskTopicEnum.NARECHIA -> createNarechiaTask(word)
                TaskTopicEnum.PARONIM -> createParonimTask(word)
                TaskTopicEnum.YDARENIA -> createYdarTask(word)
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
                ThirdBackground,
                OnSecondary2,
            )
        }

        private fun createYdarTask(word: WordTaskSpelling): TaskInterface {
            return Ydareni9Task(word.wordTask.word.value)
        }

        private fun createParonimTask(word: WordTaskSpelling): TaskInterface {
            return ParonimTask(word.spellings)
        }

        private fun createNarechiaTask(word: WordTaskSpelling): TaskInterface {
            return NarechiaTask(word.spellings, word.wordTask.taskData.contextText)
        }

        private fun unknownTopicError(topic: TaskTopicType) =
            IllegalArgumentException("Cannot resolve type of topic: $topic")


        private fun calculateColor(winRate: Double): Color {
            if (winRate < 0) {
                return Color(152, 152, 152)
            }
            return Color(((1.0 - winRate) * 2).toFloat(), (winRate * 2).toFloat(), 0F, alpha = 1F)
        }
    }

}