package com.example.russian.tasks.ydarenia

import com.example.russian.MyEnumClasses.Letters

class SingleLetter(
    val letter: String,
    val type: Letters
) {
    companion object {
        fun letterType(letter: String): Letters {
            val glasSet = hashSetOf("а", "о", "у", "е", "и", "э", "ы", "я", "ю", "ё")

            return when (letter) {
                letter.uppercase() -> Letters.YDARNA9
                in glasSet -> Letters.BESYDARNA9
                else -> Letters.SOGLASNA9
            }
        }
    }
}