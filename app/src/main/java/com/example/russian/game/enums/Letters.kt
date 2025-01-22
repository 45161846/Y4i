package com.example.russian.game.enums

enum class Letters {

    SOGLASNA9,
    YDARNA9,
    BESYDARNA9,;

    companion object {
        fun letterType(letter: String): Letters {
            val glasSet = hashSetOf("а", "о", "у", "е", "и", "э", "ы", "я", "ю", "ё")

            return when (letter) {
                letter.uppercase() -> YDARNA9
                in glasSet -> BESYDARNA9
                else -> SOGLASNA9
            }
        }

    }


}