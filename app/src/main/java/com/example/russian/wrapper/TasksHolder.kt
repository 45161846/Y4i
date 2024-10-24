package com.example.russian.wrapper

import com.example.russian.ui.state.TaskUIState
import com.example.russian.ui.state.hood.GameNavigationState
import kotlinx.coroutines.flow.MutableStateFlow

class TasksHolder(
    initialTasks: MutableList<TaskUIState>,
) : IndexedList<TaskUIState>(initialTasks) {

    override fun previous(): TaskUIState {
        val res = super.previous()
        updateNavigationState()
        return res
    }

    override fun next(): TaskUIState {
        val res = super.next()
        updateNavigationState()
        return res
    }

    override fun toLast(): TaskUIState {
        val res = super.toLast()
        updateNavigationState()
        return res
    }

    override fun add(value: TaskUIState) {
        super.add(value)
        updateNavigationState()
    }

    override fun addDontMove(value: TaskUIState) {
        super.addDontMove(value)
        updateNavigationState()
    }

    override fun pop(): TaskUIState {
        val res = super.pop()
        updateNavigationState()
        return res
    }

    override fun removeAt(ind: Int) {
        super.removeAt(ind)
        updateNavigationState()
    }


    private fun updateNavigationState(){

        currentElement.value.navigationState = GameNavigationState(
            onPreviousClick = this::previous,
            onNextClick = this::next,
            toTaskClick = this::toLast,
            onAnswerClick = currentElement.value.navigationState.onAnswerClick,
            showPrev = this.hasPrev(),
            showNext = this.hasNext(),
            showAnswerButton = currentElement.value is TaskUIState.ClickableText && !hasNext()
        )
    }
}