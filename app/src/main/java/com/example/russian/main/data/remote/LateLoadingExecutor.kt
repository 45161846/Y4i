package com.example.russian.main.data.remote

import com.example.remotelogin.wrappers.RequestResult
import com.example.russian.game.back.data.dao.LoadingDao
import com.example.russian.game.back.data.entity.MyTask
import com.example.russian.game.back.data.entity.PartOfTask
import com.example.russian.game.back.data.entity.Statistics
import com.example.russian.game.back.data.entity.TaskData
import com.example.russian.game.back.data.entity.playlist.Playlist
import com.example.russian.game.back.data.entity.playlist.PlaylistCrossRef
import com.example.russian.game.back.initialloading.impl.InitialLoadingFileReader
import com.example.russian.game.mapper.FormatToMyTaskMapper
import com.example.russian.main.Id
import com.example.russian.main.TaskType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LateLoadingAPI @Inject constructor(
    private val dao: LoadingDao,
    private val webSource: PlaylistWebSource
) {

    val loadingQueue = MutableStateFlow<List<Id>>(emptyList())

    suspend fun addNewPlaylist(
        playlist: RemotePlaylist,
        tasks: List<PreviewTask>
    ) {

        val needDownload = mutableListOf<PreviewTask>()
        val reference = mutableListOf<PlaylistCrossRef>()

        for (task in tasks) {
            if (dao.checkIfTaskExist(task.remoteId.value)) {
                val localId = dao.taskLocalIdByRemote(task.remoteId.value)
                reference.add(PlaylistCrossRef(playlist.remoteId, Id(localId)))
            } else {
                needDownload.add(task)
            }
        }

        withContext(Dispatchers.IO) {

            //TODO
            // Проверять что такой плейлист уже есть и обновлять его

            val newLocalId = async {
                 dao.addPlaylist(
                    Playlist(
                        remoteId = playlist.remoteId,
                        title = playlist.title,
                        capacity = playlist.capacity,
                        description = playlist.description
                    )
                )
            }

            val newTasks = needDownload
                .map {
                    MyTask(
                        remoteId = it.remoteId,
                        value = "",
                        topic = TaskType.NOT_DOWNLOADED,
                        isLoaded = false
                    )
                }

            val ids = dao.addNewTasks(newTasks).map { Id(it) }

            loadingQueue.update {
                ids
            }

            val newStats = needDownload.mapIndexed { ind, newTask ->
                Statistics(
                    taskId = ids[ind],
                    displayableText = newTask.previewText,
                    type = TaskType.NOT_DOWNLOADED
                )
            }

            val localPlaylistId = Id(newLocalId.await())

            val newWordsReferences = ids.map {
                PlaylistCrossRef(localPlaylistId, it)
            }

            launch { dao.addMultipleCrossRef(reference + newWordsReferences) }
            launch { dao.addStats(newStats) }
        }

    }

    suspend fun loadTasks(localId: List<Id>) {

        val remoteIds = localId.map {
            dao.remoteId(it)
        }

        val partsToDB = mutableListOf<List<PartOfTask>>()
        val words = mutableListOf<MyTask>()

        remoteIds.forEachIndexed {i, remoteId ->
            val taskRow = webSource.loadTask(remoteId)
            if (taskRow is RequestResult.TaskDownload.Data) {
                val task = FormatToMyTaskMapper.initialStringToWord(taskRow.text).copy(id = localId[i])
                words.add(task)

                dao.changeType(localId[i], task.topic)

                val parts = FormatToMyTaskMapper.wordToPartOfTask(task)
                partsToDB.add(parts)
            }
        }

        val taskData = words.map {
            TaskData(
                taskId = it.id,
                contextText = InitialLoadingFileReader.contextWord(it.value),
                displayableText = FormatToMyTaskMapper.getDisplayableText(it.value, it.topic)
            )
        }

        withContext(Dispatchers.IO) {
            launch {
                dao.addPartOfTasks(partsToDB.flatten())

                val spellingVariants = InitialLoadingFileReader.getAllSpellings(
                    dao.getAllClickableTasks().map {
                        it.PartOfTasks
                    }.flatten()
                )

                dao.addSpellings(spellingVariants)
            }
            launch {
                dao.addTaskData(taskData)
            }
        }

    }
}