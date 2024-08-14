package com.example.russian.architecture2.mapper

import com.example.russian.MyEnumClasses.TaskTopicEnum
import com.example.russian.MyEnumClasses.TaskTopicType
import com.example.russian.architecture2.backend.data.entity.WordTaskSpelling
import com.example.russian.tasks.TaskInterface
import com.example.russian.tasks.narecia.NarechiaTask
import com.example.russian.tasks.paronim.ParonimTask
import com.example.russian.tasks.ydarenia.Ydareni9Task

class WordMapper {

    companion object: WordMapperInterface{
        override fun wordToTask(word: WordTaskSpelling): TaskInterface {

            return when(val topic = word.wordTask.word.topic){
                TaskTopicEnum.NARECHIA -> createNarechiaTask(word)
                TaskTopicEnum.PARONIM -> createParonimTask(word)
                TaskTopicEnum.YDARENIA -> createYdarTask(word)
                else -> throw unknownTopicError(topic)
            }
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

        private fun unknownTopicError(topic: TaskTopicType) = IllegalArgumentException("Cannot resolve type of topic: $topic")
    }

}