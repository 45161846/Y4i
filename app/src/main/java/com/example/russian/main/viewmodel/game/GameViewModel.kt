package com.example.russian.main.viewmodel.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.russian.main.back.data.entity.MyTask
import com.example.russian.main.back.data.entity.TaskPartOfTask
import com.example.russian.main.enums.TaskTopicEnum
import com.example.russian.main.mapper.WordMapper
import com.example.russian.main.repository.arch.AnswerAPI
import com.example.russian.main.repository.arch.GameRepositoryInterface
import com.example.russian.main.repository.impl.GameRepository
import com.example.russian.main.tasks.AnswerDataAPI
import com.example.russian.main.tasks.TaskInterface
import com.example.russian.main.tasks.TaskStateMapper
import com.example.russian.main.tasks.clickable.ClickableWordsInTextTask
import com.example.russian.main.tool.SoundAPI
import com.example.russian.main.tool.VibrationAPI
import com.example.russian.main.wrapper.TasksHolder
import com.example.russian.main.ui.state.HoodUIState
import com.example.russian.main.ui.state.TaskUIState
import com.example.russian.main.ui.state.hood.GameNavigationState
import com.example.russian.main.ui.state.hood.HoodState
import com.example.russian.main.ui.state.hood.HoodStateInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameViewModel : ViewModel(), GameViewModelAPI {

    private val repo: GameRepositoryInterface = GameRepository()

    private lateinit var currentWord: MyTask

    private val hoodState: HoodStateInterface = HoodState()
    private val taskHolder: TasksHolder = TasksHolder(mutableListOf(TaskUIState.Loading))
    private lateinit var navigationState: StateFlow<GameNavigationState>

    private val _taskUiState = MutableStateFlow<TaskUIState>(TaskUIState.Loading)
    private val taskUiState: StateFlow<TaskUIState> = taskHolder.currentElement

    private var vibrator: VibrationAPI? = null
    private lateinit var soundAPI: SoundAPI

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun setNecessaryData(data: NecessaryData) {
        soundAPI = data.soundAPI
        vibrator = data.vibrationAPI

        viewModelScope.launch(viewModelScope.coroutineContext + Dispatchers.Main) {
            navigationState  = taskHolder.currentElement.map {
                GameNavigationState(
                    onPreviousClick = taskHolder::previous,
                    onNextClick = taskHolder::next,
                    toTaskClick = taskHolder::toLast,
                    onAnswerClick = {},
                    showPrev = taskHolder.hasPrev(),
                    showNext = taskHolder.hasNext(),
                    showAnswerButton = it is TaskUIState.ClickableText && !taskHolder.hasNext()
                )
            }.stateIn(viewModelScope)
        }


        if (taskHolder.currentElement.value is TaskUIState.Loading) {
            repo.setDao(data.application)

            viewModelScope.launch {

                repo.allWordsInPlaylist(
                    data.playlistId
                ).flatMapMerge {
                    repo.cacheWords(it)
                    currentWord = it.random()

                    repo.displayableWord(currentWord.id)
                }.collect {
                    assign(task = it)
                    taskHolder.removeAt(0)
                }
            }
        }
    }

    override fun uiStateFlow(): StateFlow<TaskUIState> = taskUiState

    private fun currentWord(): MyTask = currentWord

    private fun hoodState(): HoodStateInterface {
        return hoodState
    }

    private fun randomWord(): MyTask = repo.randomWord()

    private fun correct(api: AnswerDataAPI) {
        vibrator?.vibrateCorrect()

        soundAPI.playAnswerCorrect()

        viewModelScope.launch {

            save(true)

            answeredState(api)

            repo.displayableWord(randomWord().id).collect {
                assign(500L, it)
            }

        }
    }

    private fun incorrect(api: AnswerDataAPI) {

        soundAPI.playAnswerIncorrect()

        vibrator?.vibrateAnswerWrong()

        viewModelScope.launch {
            save(false)

            answeredState(api)

            repo.displayableWord(randomWord().id).collect {
                assign(1300L, it)
            }
        }
    }

    private suspend fun save(correct: Boolean) {
        //save answer result to stats in DB
        repo.saveAnswer(currentWord().id, object : AnswerAPI {
            override fun correct(): Boolean {
                return correct
            }
        })

    }

    private suspend fun assign(delay: Long = 0, task: TaskPartOfTask) {
        currentWord = task.taskNoPartOfTask.word
        val newState = wordToUIState(task, hoodState(), navigationState.value)

        delay(delay)

        withContext(Dispatchers.Main) {
            taskHolder.add(newState)
        }
    }


    private fun answeredState(api: AnswerDataAPI) {
        if (api.isCorrect()) {
            hoodState.correct()
        } else {
            hoodState.incorrect()
        }

        //show correct answer in UI
        taskHolder.currentElement.value.let { oldState ->
            val newState = TaskStateMapper.answered(oldState, api)
            taskHolder.replaceAt(-1, newState)
        }
    }

    private suspend fun wordToUIState(task: TaskPartOfTask, hoodState: HoodStateInterface, navigationState: GameNavigationState): TaskUIState {

        val newState = if (task.taskNoPartOfTask.word.topic == TaskTopicEnum.CLICKABLE) {
            val partsAndSpellings = repo.partsAndSpellings(task.taskNoPartOfTask.word.id)
            val newTask = ClickableWordsInTextTask(partsAndSpellings)

            navigationState.onAnswerClick ={
                if (newTask.answered()) {
                    correct(object : AnswerDataAPI {
                        override fun answeredIndex(): Int {
                            TODO("Not yet implemented")
                        }

                        override fun isCorrect(): Boolean {
                            return true
                        }
                    })
                } else {
                    incorrect(object : AnswerDataAPI {
                        override fun answeredIndex(): Int {
                            TODO("Not yet implemented")
                        }

                        override fun isCorrect(): Boolean {
                            return false
                        }
                    })
                }
            }

            val newState = TaskUIState.ClickableText(
                newTask.words,
                HoodUIState.NoTimer(
                    correct = hoodState().correctCounter(),
                    incorrect = hoodState().incorrectCounter()
                ),
                navigationState,
                newTask::clicked
            )
            newState
        } else {
            taskToUIState(
                wordToTask(task),
                hoodState,
                navigationState
            )
        }

        return newState
    }


    private fun wordToTask(word: TaskPartOfTask): TaskInterface {
        return WordMapper.wordToTask(word)
    }

    private fun taskToUIState(task: TaskInterface, hoodState: HoodStateInterface,
                              navigationState: GameNavigationState
    ): TaskUIState {
        return TaskStateMapper.taskToState(task, hoodState, navigationState, onClickCorrect = {
            correct(object : AnswerDataAPI {
                override fun answeredIndex(): Int {
                    return it
                }

                override fun isCorrect(): Boolean {
                    return true
                }
            })
        }, onClickIncorrect = {
            incorrect(object : AnswerDataAPI {
                override fun answeredIndex(): Int {
                    return it
                }

                override fun isCorrect(): Boolean {
                    return false
                }
            })
        })
    }
}