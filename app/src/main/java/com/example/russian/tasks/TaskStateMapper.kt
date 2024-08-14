package com.example.russian.tasks

import androidx.compose.ui.graphics.Color
import com.example.russian.architecture2.viewmodel.game.state.ButtonUIState
import com.example.russian.architecture2.viewmodel.game.state.ContentComponent
import com.example.russian.architecture2.viewmodel.game.state.HoodUIState
import com.example.russian.architecture2.viewmodel.game.state.TaskUIState
import com.example.russian.gameClasses.viewmodel.hood.HoodStateInterface
import com.example.russian.tasks.narecia.NarechiaTask
import com.example.russian.tasks.paronim.ParonimTask
import com.example.russian.tasks.ydarenia.Ydareni9Task
import com.example.russian.ui.theme.GameButtonFirstColor
import com.example.russian.ui.theme.GameButtonSecondColor
import com.example.russian.ui.theme.GameButtonThirdColor

class TaskStateMapper {

    companion object : TaskStateMapperInterface {

        override fun answered(
            previousState: TaskUIState,
            api: AnswerDataAPI
        ): TaskUIState {

            when (previousState) {
                is TaskUIState.TaskUI -> return answeredNotYdarenia(
                    previousState, api.answeredIndex(), api.isCorrect()
                )

                else -> TODO()
            }
        }

        private fun answeredNotYdarenia(
            previousState: TaskUIState.TaskUI,
            answeredIndex: Int,
            isCorrect: Boolean
        ): TaskUIState.TaskUI {

            val hood = previousState.hoodState
            when (hood) {
                is HoodUIState.NoTimer -> {
                    hood.answer(isCorrect)
                }
            }
            val buttonStates = List(previousState.buttonStates.size){
                val oldState = previousState.buttonStates[it]
                val itWasClicked = answeredIndex == it

                if(oldState !is ButtonUIState.InProgress) throw RuntimeException() //can never happen

                val newBorderColor = if(itWasClicked) oldState.newBorderColor else oldState.borderColor

                ButtonUIState.ShowAnswer(
                    text =  oldState.buttonText,
                    backColor =  oldState.backColor,
                    borderColor =  newBorderColor
                )
            }

            return TaskUIState.TaskUI(
                buttonStates,
                hood
            )
        }

        override fun answeredIndexToState(
            answeredIndex: Int,
            state: TaskUIState
        ): ContentComponent {
            when (state) {
                is TaskUIState.TaskUI -> {
                    return state.buttonStates[answeredIndex]
                }

                is TaskUIState.YdareniaTaskUI -> {
                    return state.letterStates[answeredIndex]
                }
                //can never happen
                else -> throw IllegalArgumentException("Answered on loading")
            }
        }

        override fun taskToState(
            task: TaskInterface,
            hoodStateInterface: HoodStateInterface,
            onClickCorrect: (Int) -> Unit,
            onClickIncorrect: (Int) -> Unit,
        ): TaskUIState {
            return when (task) {
                is Ydareni9Task -> TODO("Not yet implemented")
                is ParonimTask -> buttonsUiState(
                    task,
                    hoodStateInterface,
                    onClickCorrect,
                    onClickIncorrect
                )

                is NarechiaTask -> buttonsUiState(
                    task,
                    hoodStateInterface,
                    onClickCorrect,
                    onClickIncorrect
                )
                //can never happen
                else -> throw IllegalArgumentException("Cannot cast object (type TaskInterface) to TaskUIState")
            }
        }

        private fun buttonsUiState(
            task: TaskInterface,
            hoodStateInterface: HoodStateInterface,
            onClickCorrect: (Int) -> Unit,
            onClickIncorrect: (Int) -> Unit,
        ): TaskUIState.TaskUI {
            val variants = task.getPosibleVariants()

            val buttonStates = List(variants.size) { ind ->

                val correct = task.isCorrect(ind)
                val click: (Int) -> Unit = if (correct) onClickCorrect else onClickIncorrect
                val newBorderColor = if (correct) Color.Green else Color.Red

                ButtonUIState.InProgress(
                    variants[ind],
                    buttonColor(ind),
                    onClick = { click(ind) },
                    newBorderColor = newBorderColor
                )
            }

            val hoodUIState = HoodUIState.NoTimer(
                correct = hoodStateInterface.correctCounter(),
                incorrect = hoodStateInterface.incorrectCounter()
            )

            return TaskUIState.TaskUI(buttonStates, hoodUIState)
        }

        private fun buttonColor(index: Int) = mapOf(
            0 to GameButtonFirstColor,
            1 to GameButtonSecondColor,
            2 to GameButtonThirdColor,
            3 to Color.White,
            4 to Color.Blue
        )[index] ?: throw RuntimeException("Too many options, not enough colors")
    }


}


interface TaskStateMapperInterface {
    fun answered(
        previousState: TaskUIState,
        api: AnswerDataAPI
    ): TaskUIState

    fun taskToState(
        task: TaskInterface,
        hoodStateInterface: HoodStateInterface,
        onClickCorrect: (Int) -> Unit,
        onClickIncorrect: (Int) -> Unit,
    ): TaskUIState

    fun answeredIndexToState(
        answeredIndex: Int,
        state: TaskUIState
    ): ContentComponent
}

interface AnswerDataAPI {
    fun answeredIndex(): Int

    fun isCorrect(): Boolean
}