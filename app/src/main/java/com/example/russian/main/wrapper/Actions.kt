package com.example.russian.main.wrapper

sealed class StateActions{

    data class DefaultStateMapperActions(
        val onClickCorrect: (Int) -> Unit,
        val onClickIncorrect: (Int) -> Unit,
    )

    data class ClickableTextActions(
        val onWordClick: (Int) -> String
    )
}