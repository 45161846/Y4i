package com.example.russian.architecture2.viewmodel.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.russian.architecture2.backend.data.entity.NewWord
import com.example.russian.architecture2.backend.data.entity.WordTaskSpelling
import com.example.russian.architecture2.mapper.WordMapper
import com.example.russian.architecture2.repository.arch.AnswerAPI
import com.example.russian.architecture2.repository.arch.GameRepositoryInterface
import com.example.russian.architecture2.repository.impl.GameRepository
import com.example.russian.architecture2.viewmodel.game.state.TaskUIState
import com.example.russian.gameClasses.viewmodel.hood.HoodState
import com.example.russian.gameClasses.viewmodel.hood.HoodStateInterface
import com.example.russian.tasks.AnswerDataAPI
import com.example.russian.tasks.TaskInterface
import com.example.russian.tasks.TaskStateMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameViewModel : ViewModel(), GameViewModelAPI {

    private val repo: GameRepositoryInterface = GameRepository()

    private val _taskUiState = MutableStateFlow<TaskUIState>(TaskUIState.Loading)

    private val taskUiState: StateFlow<TaskUIState> = _taskUiState

    private lateinit var currentWord: NewWord

    private val hoodState: HoodStateInterface = HoodState()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun setNecessaryData(data: NecessaryData) {

        repo.setDao(data.application)

        viewModelScope.launch {

            repo.allWordsInPlaylist(
                data.playlistId
            ).flatMapMerge {
                repo.cacheWords(it)
                currentWord = it.random()

                repo.displayableWord(currentWord.id)
            }.collect {
                assign(word = it)
            }

        }
    }

    override fun uiStateFlow(): StateFlow<TaskUIState> = taskUiState

    private fun currentWord(): NewWord = currentWord

    private fun hoodState(): HoodStateInterface = hoodState

    private fun randomWord(): NewWord = repo.randomWord()

    private fun correct(api: AnswerDataAPI) {

        viewModelScope.launch {

            save(true)

            answeredState(api)

            repo.displayableWord(randomWord().id).collect {
                assign(500L, it)
            }

        }
    }

    private fun incorrect(api: AnswerDataAPI) {
        viewModelScope.launch {
            save(false)

            answeredState(api)

            repo.displayableWord(randomWord().id).collect {
                assign(500L, it)
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

    private fun answeredState(api: AnswerDataAPI) {
        //show correct answer in UI
        _taskUiState.value.let { oldState ->
            _taskUiState.value = TaskStateMapper.answered(oldState, api)
        }
    }

    private suspend fun assign(delay: Long = 0, word: WordTaskSpelling) {

        currentWord = word.wordTask.word
        val newState = wordToUIState(word, hoodState())

        delay(delay)

        withContext(Dispatchers.Main) {
            _taskUiState.value = newState
        }
    }


    private fun wordToUIState(word: WordTaskSpelling, hoodState: HoodStateInterface) =
        taskToUIState(
            wordToTask(word),
            hoodState
        )

    private fun wordToTask(word: WordTaskSpelling): TaskInterface {
        return WordMapper.wordToTask(word)
    }

    private fun taskToUIState(task: TaskInterface, hoodState: HoodStateInterface): TaskUIState {
        return TaskStateMapper.taskToState(task, hoodState, onClickCorrect = {
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