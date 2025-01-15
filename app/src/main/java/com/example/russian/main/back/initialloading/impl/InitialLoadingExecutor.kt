package com.example.russian.main.back.initialloading.impl

import android.content.res.AssetManager
import com.example.russian.architectured.Id
import com.example.russian.architectured.TaskType
import com.example.russian.main.back.data.dao.LoadingDao
import com.example.russian.main.back.data.entity.Statistics
import com.example.russian.main.back.data.entity.TaskData
import com.example.russian.main.back.data.entity.playlist.Playlist
import com.example.russian.main.back.data.entity.playlist.PlaylistCrossRef
import com.example.russian.main.back.initialloading.arch.InitialLoadingExecutor
import com.example.russian.main.mapper.FormatToMyTaskMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class InitialLoadingExecutor(
    private val dao: LoadingDao,
    private val assertManager: AssetManager,
) : InitialLoadingExecutor {


    override suspend fun execute() {

        withContext(Dispatchers.IO) {
            val loadingHandler = InitialLoadingFileReader(
                assertManager
            )

            val job1Completed = async {
                val doILoadInitialWords = dao.checkIfNoneWordExist()

                if (doILoadInitialWords) {
                    val initialWords = loadingHandler.getAllWords()

                    dao.addWords(initialWords)

                    val words = dao.getAllWordsList()

                    val mapper = FormatToMyTaskMapper
                    val stats = words.map {
                        Statistics(
                            taskId = it.id,
                            displayableText = mapper.getDisplayableText(it.value, it.topic),
                            type = it.topic
                        )
                    }

                    val taskData = words.map {
                        TaskData(
                            taskId = it.id,
                            contextText = loadingHandler.contextWord(it.value),
                            displayableText = mapper.getDisplayableText(it.value, it.topic)
                        )
                    }

                    val partOfTasks = loadingHandler.getAllPartOfTasksToDBWords(words)



                    launch {
                        dao.addPartOfTasks(partOfTasks)

                        val spellingVariants = loadingHandler.getAllSpellings(
                            dao.getAllClickableTasks().map {
                                it.PartOfTasks
                            }.flatten()
                        )

                        dao.addSpellings(spellingVariants)
                    }
                    launch {
                        dao.addStats(stats)
                    }
                    launch {
                        dao.addTaskData(taskData)
                    }

                }
                true
            }

            val job2Completed = async {
                val doILoadInitialPlaylists = dao.checkIfNonePlaylistExist()

                if (doILoadInitialPlaylists) {
                    val initialPlaylists = initialPlaylists()
                    dao.addPlaylists(initialPlaylists)
                }
                true
            }

            //need to wait until words are added to DB
            job1Completed.await()
            job2Completed.await()
            //at this point words and playlists are added, so we can create their crossref
            launch {
                val doILoadInitialCrossRefConnections = dao.checkIfNoneCrossExist()

                if (doILoadInitialCrossRefConnections) {
                    val words = dao.getAllWordsList()

                    val crossRefs = List(words.size) {

                        //id in crossRef DB starts from 1, so topic value correlates with default playlist
                        PlaylistCrossRef(
                            taskId = words[it].id,
                            playlistId = playlistIdByType(words[it].topic )
                        )
                    }

                    dao.addMultipleCrossRef(crossRefs)
                }
            }
        }

    }


    private fun initialPlaylists() = listOf(
        Playlist(id = Id(1), title = "Наречия", capacity = 0,),
        Playlist(id = Id(2),title = "Паронимы", capacity = 0,),
        Playlist(id = Id(3),title = "Ударения", capacity = 0,),
        Playlist(id = Id(4),title = "Запятые", capacity = 0)
    )

    private fun playlistIdByType(type: TaskType) = when(type){
        TaskType.NARECHIA -> Id(1)
        TaskType.PARONIM -> Id(2)
        TaskType.YDARENIA -> Id(3)
        TaskType.CLICKABLE -> Id(4)
    }
}