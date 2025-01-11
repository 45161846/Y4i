package com.example.russian.main.ui.state.hood

data class GameNavigationState(
    val onPreviousClick: () -> Unit,
    val onNextClick: () -> Unit,
    val toTaskClick: () -> Unit,
    var onAnswerClick: () -> Unit,
    val showPrev: Boolean,
    val showNext: Boolean,
    val showAnswerButton : Boolean
)