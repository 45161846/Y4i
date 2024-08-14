package com.example.russian.toolPackage

import com.example.russian.MyEnumClasses.TaskTopic
import com.example.russian.architecture.data.olddata.entity.Word

class WordFieldsMapper {

    companion object{

        fun getWordContext(word: Word): String{

            val parts = word.value.split(";")

            return if(parts.size == 1 || parts.last().isEmpty()){
                ""
            }else{
                parts.last()
            }
        }

        fun getDisplayableText(word: Word): String{
            return when (word.topic) {
                TaskTopic().NARECHI9 -> {
                    word.value.split(";")[0].split("|")[0]
                }

                TaskTopic().PARONIM -> {
                    val paronim = WordToTaskMapper.getAllParonimsNoCotext(word.value)
                    paronim.joinToString(" - ")
                }

                TaskTopic().YDARENI9 -> {
                    word.value
                }

                else -> {
                    "Unknown word"
                }

            }
        }
    }
}