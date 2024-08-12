package com.example.russian.gameClasses.activity.draw

import androidx.compose.ui.graphics.Color
import com.example.russian.tasks.ydarenia.SingleLetter

sealed class TaskUIState {

    data object Loading : TaskUIState()

    data class TaskUI(
        val buttonStates: List<ButtonUIState>,
        val hoodState: HoodUIState
    ) : TaskUIState()

    data class YdareniaTaskUI(
        val letterStates: List<LetterUIState>,
        val hoodState: HoodUIState
    ) : TaskUIState()
}

sealed class ContentComponent(
    open val onClick: () -> Unit
)

sealed class ButtonUIState(
    val buttonText: String,
    open val backColor: Color,
    override val onClick: () -> Unit
) : ContentComponent(onClick) {

    data class InProgress(
        val text: String,
        override val backColor: Color,
        val borderColor: Color = Color.Black,
        val newBorderColor: Color,
        override val onClick: () -> Unit
    ) : ButtonUIState(text, backColor, onClick)

    data class ShowAnswer(val text: String, override val backColor: Color, val borderColor: Color = Color.Black) : ButtonUIState(text, backColor, {})

}

sealed class LetterUIState(
    override val onClick: () -> Unit
) : ContentComponent(onClick) {
    data class Sogl(val letter: SingleLetter, var color: Color) : LetterUIState({})

    data class Glas(val index: Int, val letter: SingleLetter, var color: Color, override val onClick: () -> Unit) :
        LetterUIState(onClick)
}

sealed class HoodUIState {

    class NoTimer(
        var correct: Int,
        var incorrect: Int
    ) : HoodUIState() {
        private fun correct() {
            correct++
        }

        private fun incorrect() {
            incorrect++
        }

        fun answer(isCorrect: Boolean) {
            if (isCorrect) correct() else incorrect()
        }
    }
}

