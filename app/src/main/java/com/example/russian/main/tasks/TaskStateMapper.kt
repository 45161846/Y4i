package com.example.russian.main.tasks

import androidx.compose.ui.graphics.Color
import com.example.russian.main.enums.Letters
import com.example.russian.main.tasks.narecia.NarechiaTask
import com.example.russian.main.tasks.paronim.ParonimTask
import com.example.russian.main.tasks.ydarenia.SingleLetter
import com.example.russian.main.tasks.ydarenia.Ydareni9Task
import com.example.russian.main.ui.state.ButtonUIState
import com.example.russian.main.ui.state.ClickableWord
import com.example.russian.main.ui.state.ContentComponent
import com.example.russian.main.ui.state.ContextTextState
import com.example.russian.main.ui.state.HoodUIState
import com.example.russian.main.ui.state.LetterUIState
import com.example.russian.main.ui.state.TaskUIState
import com.example.russian.main.ui.state.hood.GameNavigationState
import com.example.russian.main.ui.state.hood.HoodStateInterface
import com.example.russian.main.ui.theme.GameButtonFirstColor
import com.example.russian.main.ui.theme.GameButtonSecondColor
import com.example.russian.main.ui.theme.GameButtonThirdColor

class TaskStateMapper {

    companion object : TaskStateMapperInterface {

        override fun answered(
            previousState: TaskUIState,
            api: AnswerDataAPI
        ): TaskUIState {

            when (previousState) {
                is TaskUIState.TaskUI -> return answeredNotYdarenia(
                    previousState, api.answeredIndex(), api.isCorrect(), previousState.navigationState
                )

                is TaskUIState.YdareniaTaskUI -> return answeredYdarenia(
                    previousState, api.answeredIndex(), api.isCorrect(), previousState.navigationState
                )
                is TaskUIState.ClickableText -> return answeredClickable(
                    previousState, api.isCorrect()
                )
                else -> throw RuntimeException("You have managed to answer in loading process. HOW?")//can never happen
            }
        }

        private fun answeredClickable(
            previousState: TaskUIState.ClickableText,
            correct: Boolean,
        ): TaskUIState {
            return TaskUIState.ClickableText(
                words = previousState.words.map {
                    when(it){
                        is ClickableWord.NoClick -> it
                        is ClickableWord.Clickable -> {
                            if(it.correct){
                                ClickableWord.NoClick(it.textFlow.value.replace("[ +](?= )".toRegex(), "_"), Color.Green)
                            }else{
                                ClickableWord.NoClick(it.textFlow.value.replace("[ +](?= )".toRegex(), "_"), Color.Red)
                            }
                        }
                    }
                },
                newHood(previousState.hoodState, correct),
                previousState.navigationState,
                previousState.onWordClick
            )
        }

        private fun answeredNotYdarenia(
            previousState: TaskUIState.TaskUI,
            answeredIndex: Int,
            isCorrect: Boolean,
            navigationState: GameNavigationState
        ): TaskUIState.TaskUI {

            val hood = newHood(previousState.hoodState, isCorrect)
            val buttonStates = List(previousState.buttonStates.size){
                val oldState = previousState.buttonStates[it]
                val itWasClicked = answeredIndex == it

                if(oldState !is ButtonUIState.InProgress) {
                    throw RuntimeException("Some how click happened faster then UI changed")
                } //can never happen. But happened once

                val newBorderColor = if(itWasClicked) oldState.newBorderColorIfClicked else oldState.newBorderColorIfNothing

                ButtonUIState.ShowAnswer(
                    text =  oldState.buttonText,
                    backColor =  oldState.backColor,
                    borderColor =  newBorderColor
                )
            }

            return TaskUIState.TaskUI(
                buttonStates,
                previousState.contextState,
                hood,
                navigationState
            )
        }

        private fun answeredYdarenia(
            previousState: TaskUIState.YdareniaTaskUI,
            answeredIndex: Int,
            isCorrect: Boolean,
            navigationState: GameNavigationState
        ): TaskUIState.YdareniaTaskUI{
            val hood = newHood(previousState.hoodState, isCorrect)
            val letterStates = previousState.letterStates.mapIndexed{ind, it ->
                val itWasClicked = answeredIndex == ind
                when(it){
                    is LetterUIState.Sogl -> LetterUIState.Sogl(it.letter, it.color)
                    is LetterUIState.Glas -> LetterUIState.Glas(
                        ind,
                        it.letter,
                        if (itWasClicked) it.nextColorClicked else it.nextColorNotClicked,
                        Color.Black,
                        Color.Black,
                    ){}
                }

            }

            return TaskUIState.YdareniaTaskUI(
                letterStates,
                hood,
                navigationState
            )
        }

        override fun answeredIndexToState(
            answeredIndex: Int,
            state: TaskUIState,
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
            navigationState: GameNavigationState,
            onClickCorrect: (Int) -> Unit,
            onClickIncorrect: (Int) -> Unit,
        ): TaskUIState {

            return when (task) {
                is Ydareni9Task -> ydarUiState(
                        task,
                        hoodStateInterface,
                    navigationState,
                        onClickCorrect,
                        onClickIncorrect
                    )

                is ParonimTask -> buttonsUiState(
                    task,
                    hoodStateInterface,
                    navigationState,
                    onClickCorrect,
                    onClickIncorrect
                )

                is NarechiaTask -> buttonsUiState(
                    task,
                    hoodStateInterface,
                    navigationState,
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
            navigationState: GameNavigationState,
            onClickCorrect: (Int) -> Unit,
            onClickIncorrect: (Int) -> Unit,
        ): TaskUIState.TaskUI {
            val variants = task.getPosibleVariants()

            val buttonStates = List(variants.size) { ind ->

                val correct = task.isCorrect(ind)
                val click: (Int) -> Unit = if (correct) onClickCorrect else onClickIncorrect
                val newBorderColorIfClicked = if (correct) Color.Green else Color.Red
                val newBorderColorIfNothing = if (correct) Color.Green else Color.Black

                ButtonUIState.InProgress(
                    variants[ind],
                    buttonColor(ind),
                    onClick = { click(ind) },
                    newBorderColorIfClicked = newBorderColorIfClicked,
                    newBorderColorIfNothing = newBorderColorIfNothing
                )
            }

            val hoodUIState = HoodUIState.NoTimer(
                correct = hoodStateInterface.correctCounter(),
                incorrect = hoodStateInterface.incorrectCounter()
            )

            val contextTextState = if(task.getTaskText().isEmpty()){
                ContextTextState.NoContext
            }else{
                ContextTextState.Context(
                    text = task.getTaskText(),
                    textColor = Color.White,
                    lineColor = Color.Black
                )
            }

            return TaskUIState.TaskUI(
                buttonStates,
                contextTextState,
                hoodUIState,
                navigationState
            )
        }

        private fun buttonColor(index: Int) = mapOf(
            0 to GameButtonFirstColor,
            1 to GameButtonSecondColor,
            2 to GameButtonThirdColor,
            3 to Color.White,
            4 to Color.Blue
        )[index] ?: throw RuntimeException("Too many options, not enough colors")

        private fun ydarUiState(
            task: TaskInterface,
            hoodStateInterface: HoodStateInterface,
            navigationState: GameNavigationState,
            onClickCorrect: (Int) -> Unit,
            onClickIncorrect: (Int) -> Unit,
        ): TaskUIState.YdareniaTaskUI{
            val variants = task.getPosibleVariants()

            val letterStates = List(variants.size) { ind ->

                val correct = task.isCorrect(ind)
                val click: (Int) -> Unit = if (correct) onClickCorrect else onClickIncorrect
                val newBorderColorIfClicked = if (correct) Color.Green else Color.Red
                val newBorderColorIfNothing = if (correct) Color.Green else Color.Black

                when(SingleLetter.letterType(variants[ind])){
                    Letters.YDARNA9 -> LetterUIState.Glas(
                        ind,
                        SingleLetter(variants[ind], Letters.YDARNA9),
                        Color.Black,
                        newBorderColorIfClicked,
                        newBorderColorIfNothing
                    ){
                        click(ind)
                    }
                    Letters.BESYDARNA9 -> LetterUIState.Glas(
                        ind,
                        SingleLetter(variants[ind], Letters.YDARNA9),
                        Color.Black,
                        newBorderColorIfClicked,
                        newBorderColorIfNothing){
                        click(ind)
                    }
                    Letters.SOGLASNA9 -> LetterUIState.Sogl(SingleLetter(variants[ind], Letters.SOGLASNA9), Color.Black)
                }
            }

            val hoodUIState = HoodUIState.NoTimer(
                correct = hoodStateInterface.correctCounter(),
                incorrect = hoodStateInterface.incorrectCounter()
            )
            return TaskUIState.YdareniaTaskUI(letterStates, hoodUIState, navigationState)
        }

        private fun newHood(hoodState: HoodUIState, correct: Boolean):HoodUIState{
            when (hoodState) {
                is HoodUIState.NoTimer -> {
                    hoodState.answer(correct)
                }
            }
            return hoodState
        }
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
        navigationState: GameNavigationState,
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