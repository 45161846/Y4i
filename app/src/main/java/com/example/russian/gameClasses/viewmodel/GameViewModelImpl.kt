package com.example.russian.gameClasses.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.russian.architecture.CustomApplication
import com.example.russian.architecture.data.entity.Spelling
import com.example.russian.architecture.data.entity.Word
import com.example.russian.architecture.data.entity.WordWithSpellings
import com.example.russian.gameClasses.activity.draw.TaskUIState
import com.example.russian.gameClasses.repo.Result
import com.example.russian.gameClasses.repo.WordSpellingRepositoryArch
import com.example.russian.gameClasses.viewmodel.hood.HoodState
import com.example.russian.gameClasses.viewmodel.hood.HoodStateInterface
import com.example.russian.gameClasses.viewmodel.randomaizer.CorrectDetector
import com.example.russian.gameClasses.viewmodel.randomaizer.RandomStorage
import com.example.russian.gameClasses.viewmodel.randomaizer.RandomStorageImpl
import com.example.russian.tasks.TaskInterface
import com.example.russian.tasks.TaskStateMapper
import com.example.russian.toolPackage.WordToTaskMapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class GameViewModelImpl(
    application: CustomApplication,
    playlistId: Int,
) : AndroidViewModel(application = application), GameViewModelArch {

    private val repo: WordSpellingRepositoryArch = application.wordSpellingRepo()

    private var currentWord: WordWithSpellings? = null

    private val spellingsOrderStorage: RandomStorage<Spelling> = RandomStorageImpl(3)

    private val hoodState: HoodStateInterface = HoodState()

    private val _taskUiState = MutableStateFlow<TaskUIState>(TaskUIState.Loading)

    val taskUiState: StateFlow<TaskUIState> = _taskUiState

    init {
        viewModelScope.launch {
            repo.loadGamePlaylist(playlistId)
        }

        viewModelScope.launch {
            repo.wordsFlow()
                .map{newWords ->

                    currentWord?.let { oldWord ->

                        val oldWordUpdated = checkWordInList(oldWord.word, newWords)

                        oldWordUpdated?.let { //old word still presents in Playlist
                            repo.getCurrentSpelling(it.id).collect{ wordWithSpellings ->

                                //если spellings поменялся, то надо обновлять
                                //они могли не поменяться из-за обновления БД при сохранении
                                if(!compareSpellingLists(wordWithSpellings.spellings, spellingsOrderStorage.savedRandom())){

                                    spellingsOrderStorage.setNewValues(
                                        wordWithSpellings.spellings,
                                        object : CorrectDetector<Spelling>{
                                            override fun isCorrect(obj: Spelling): Boolean {
                                                return obj.isCorrect
                                            }
                                        }
                                    )
                                    spellingsOrderStorage.shuffle()

                                    updateUI(wordWithSpellings.word, spellingsOrderStorage)
                                }


                            }
                        } ?: assignNewTask() // old word no longer in list

                    } ?: assignNewTask()
                }.collect{}
        }

//        repo.savedWords.observeForever {
//            viewModelScope.launch {
//
//                currentWord?.let { oldWord -> //old word has changed
//                    repo.getCurrentSpelling(oldWord.word.id).collect { newWord ->
//                        updateUI(newWord)
//                    }
//                } ?: assignNewTask()
//            }
//        }
    }

    private fun checkWordInList(word: Word, list: List<Word>): Word?{
        list.forEach {
            if(it.id == word.id){
                return it
            }
        }
        return null
    }

    private fun compareSpellingLists(list1: List<Spelling>, list2: List<Spelling>): Boolean{

        if(list1.size != list2.size){
            return false
        }
        var res = true
        list1.forEach {first ->
            var hasSame = false
            list2.forEach{second ->
                if (first == second){
                    hasSame = true
                }
            }
            res = res && hasSame
        }
        return res
    }

    private fun updateUI(word: Word?, randomStorage: RandomStorage<Spelling>){
        word ?: throw IllegalArgumentException("Word to show in UI is null.")

        val currentTask = (WordToTaskMapper.wordWithSpellingToTask(word, randomStorage))
        _taskUiState.value = TaskStateMapper.taskToState(currentTask, getHood(),
            onClickCorrect = {
                viewModelScope.launch {
                    correct(it)
                }
            },
            onClickIncorrect = {
                viewModelScope.launch {
                    incorrect(it)
                }
            }
        )
    }

    private suspend fun assignNewTask(delay: Long = 0) {
        delay(delay)
        repo.getRandomWordWithSpellingFlow().collect { word ->

            if(!compareSpellingLists(word.spellings, spellingsOrderStorage.savedRandom())) {

                spellingsOrderStorage.setNewValues(
                    word.spellings,
                    object : CorrectDetector<Spelling> {
                        override fun isCorrect(obj: Spelling): Boolean {
                            return obj.isCorrect
                        }
                    }
                )
                spellingsOrderStorage.shuffle()
                updateUI(word.word, spellingsOrderStorage)
                currentWord = word
            }
        }
    }

    override fun currentWord(): WordWithSpellings =
        currentWord ?: throw RuntimeException("Word has not been assigned yet.")

    override fun getHood() = hoodState

    override suspend fun correct(answeredIndex: Int) {
        hoodState.correct()

        _taskUiState.value.let {
            _taskUiState.value = TaskStateMapper.answered(it, answeredIndex, true)
        }

        currentWord?.let {
            delay(500L)

            //из-за того что ответ меняет базу данных, флоу слова по новой собирает данные и перемешивает варианты ответа в таске
            repo.answerSaveResult(Result.CORRECT, it.word.id)

            assignNewTask(1500L)
        } ?: throw RuntimeException("Word has not been assigned yet.")
    }

    override suspend fun incorrect(answeredIndex: Int) {
        hoodState.incorrect()

        _taskUiState.value.let {
            _taskUiState.value = TaskStateMapper.answered(it, answeredIndex, false)
        }

        currentWord?.let {
            delay(500L)

            repo.answerSaveResult(Result.INCORRECT, it.word.id)

            assignNewTask(100L)
        } ?: throw RuntimeException("Word has not been assigned yet.")
    }

    private fun wordToTask(word: WordWithSpellings?): TaskInterface =
        word?.let {
            WordToTaskMapper.wordWithSpellingToTask(it)
        } ?: throw RuntimeException("Word has not been assigned yet.")

}

