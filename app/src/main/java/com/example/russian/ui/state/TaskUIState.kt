package com.example.russian.ui.state

import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import com.example.russian.tasks.ydarenia.SingleLetter
import com.example.russian.ui.state.hood.GameNavigationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed class TaskUIState(
    open var navigationState: GameNavigationState
) {

    data object Loading : TaskUIState(
        GameNavigationState({},{},{},{},false,false,false)
    )

    data class TaskUI(
        val buttonStates: List<ButtonUIState>,
        val contextState: ContextTextState,
        val hoodState: HoodUIState,
        override var navigationState: GameNavigationState
    ) : TaskUIState(navigationState)

    data class YdareniaTaskUI(
        val letterStates: List<LetterUIState>,
        val hoodState: HoodUIState,
        override var navigationState: GameNavigationState
    ) : TaskUIState(navigationState)

    data class ClickableText(
        val words: List<ClickableWord>,
        val hoodState: HoodUIState,
        override var navigationState: GameNavigationState,
        val onWordClick: (Int) -> Unit,
    ): TaskUIState(navigationState)
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
        val newBorderColorIfClicked: Color,
        val newBorderColorIfNothing: Color,
        override val onClick: () -> Unit
    ) : ButtonUIState(text, backColor, onClick)

    data class ShowAnswer(
        val text: String,
        override val backColor: Color,
        val borderColor: Color = Color.Black
    ) : ButtonUIState(text, backColor, {})

}

sealed class LetterUIState(
    override val onClick: () -> Unit
) : ContentComponent(onClick) {
    data class Sogl(val letter: SingleLetter, var color: Color) : LetterUIState({})

    data class Glas(
        val index: Int,
        val letter: SingleLetter,
        var color: Color,
        val nextColorClicked: Color,
        val nextColorNotClicked: Color,
        override val onClick: () -> Unit
    ) :
        LetterUIState(onClick)
}

sealed class ClickableWord(
    open var text: String
){

    data class NoClick(
        override var text: String,
        val color: Color = Color.White
    ): ClickableWord(text)

    data class Clickable(
        val textFlow: MutableStateFlow<String>,
        var correct: Boolean
    ): ClickableWord(textFlow.value)
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

sealed class ContextTextState {

    data object NoContext : ContextTextState()

    data class Context(
        val text: String,
        val textColor: Color,
        val lineColor: Color
    ) : ContextTextState()

}