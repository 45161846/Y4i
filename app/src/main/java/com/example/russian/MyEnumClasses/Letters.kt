package com.example.russian.MyEnumClasses

enum class Letters {

    SOGLASNA9,
    YDARNA9,
    BESYDARNA9,;

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