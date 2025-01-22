package com.example.russian.game.ui.draw.test

import com.example.russian.game.ui.state.ClickableWord
import kotlinx.coroutines.flow.MutableStateFlow


private lateinit var onWordClick: (Int) -> Unit

fun testClickableWord(): List<ClickableWord> {

    var counter = 0

    val states = listOf<ClickableWord>(
        (ClickableWord.Clickable(MutableStateFlow("Word 1"),true)),
        (ClickableWord.Clickable(MutableStateFlow("Word 2"), true)),
        (ClickableWord.NoClick("Word 3"))
    )

    onWordClick = {
        when(val word = states[it]){
            is ClickableWord.Clickable -> word.textFlow.value += "abx "
            is ClickableWord.NoClick -> {}
        }
    }

    return states
}

fun testWordClickFun(): (Int) -> Unit {
    return onWordClick
}