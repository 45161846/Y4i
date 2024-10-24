package com.example.russian.mapper

import com.example.russian.back.data.entity.MyTask
import com.example.russian.back.data.entity.PartOfTask
import com.example.russian.enums.TaskTopicEnum
import com.example.russian.enums.TaskTopicType

class FormatToMyTaskMapper {

    companion object : FormatToMyTaskMapperInterface {
        override fun getDisplayableText(inputValue: String, topic: TaskTopicType): String {

            return when (topic) {
                TaskTopicEnum.NARECHIA -> narechiaText(inputValue)
                TaskTopicEnum.PARONIM -> paronimText(inputValue)
                TaskTopicEnum.YDARENIA -> ydarText(inputValue)
                TaskTopicEnum.CLICKABLE -> clickableText(inputValue)
                else -> throw RuntimeException(
                    "Cannot get displayable text for word: $inputValue."
                            + "Of topic $topic"
                )
            }
        }

        private fun clickableText(input: String): String{
            val regex = "[\\[\\]]".toRegex()
            val parts = input.split(regex)

            val partsReplacedMyCharacters = List(parts.size){
                val part = parts[it]
                val p2 = part.split("|")
                if(p2.size == 1){
                    p2[0]
                }else{
                    p2.first { str ->
                        str.contains("*")
                    }.replace("*", "")
                }
            }
            return partsReplacedMyCharacters.joinToString(separator = "")
        }

        override fun initialStringToWord(input: String, topic: TaskTopicType): MyTask {
            return MyTask(
                value = input,
                topic = topic
            )
        }

        override fun wordToPartOfTask(word: MyTask): List<PartOfTask> {

            return when (word.topic) {

                TaskTopicEnum.NARECHIA -> getAllNarechiaPartOfTasks(word)

                TaskTopicEnum.PARONIM -> getAllParonimPartOfTasks(word)

                TaskTopicEnum.YDARENIA -> getAllYdareniaPartOfTasks(word)

                TaskTopicEnum.CLICKABLE -> getAllClickableParts(word)

                else -> {
                    throw IllegalArgumentException("Cannot get PartOfTasks for word: ${word.value}. Of topic: ${word.topic}")
                }
            }

        }

        private fun getAllClickableParts(task: MyTask): List<PartOfTask> {
            val regex = "[\\[\\]]".toRegex()
            return task.value.split(regex)
                .mapIndexed { ind, strPart ->
                    PartOfTask(taskId = task.id, value = strPart, index = ind, isCorrect = true)
                }

        }

        private fun getAllNarechiaPartOfTasks(word: MyTask): List<PartOfTask> {
            val PartOfTaskStrData = word.value.split(";")[0]

            val PartOfTasksStr = PartOfTaskStrData.split("|")

            return List(PartOfTasksStr.size) {
                PartOfTask(
                    taskId = word.id,
                    value = PartOfTasksStr[it],

                    isCorrect = it == 0
                )
            }
        }

        private fun getAllParonimPartOfTasks(word: MyTask): List<PartOfTask> {
            val parts = word.value.split(" - ")
            return List(parts.size) {
                PartOfTask(
                    taskId = word.id,
                    value = parts[it],
                    isCorrect = true
                )
            }
        }

        private fun getAllYdareniaPartOfTasks(word: MyTask): List<PartOfTask> {
            return listOf(
                PartOfTask(
                    taskId = word.id,
                    value = word.value,
                    isCorrect = true
                )
            )
        }

        private fun narechiaText(input: String) = input.split("|")[0]

        private fun paronimText(input: String) = input
            .split(" - ")
            .joinToString(separator = " ") {
                it.split(" ")[0]
            }

        private fun ydarText(input: String) = input
    }
}