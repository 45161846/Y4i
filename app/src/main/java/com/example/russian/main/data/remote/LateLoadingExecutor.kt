package com.example.russian.main.data.remote

import com.example.remotelogin.wrappers.RequestResult
import com.example.russian.game.back.data.dao.LoadingDao
import com.example.russian.game.back.data.entity.MyTask
import com.example.russian.game.back.data.entity.Statistics
import com.example.russian.game.back.data.entity.playlist.Playlist
import com.example.russian.game.back.data.entity.playlist.PlaylistCrossRef
import com.example.russian.game.mapper.FormatToMyTaskMapper
import com.example.russian.main.Id
import com.example.russian.main.TaskType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LateLoadingAPI @Inject constructor(
    private val dao: LoadingDao,
    private val webSource: PlaylistWebSource
) {

    val loadingQueue: Flow<List<Id>> = dao.unloadedTasksId()

    suspend fun addNewPlaylist(
        playlist: RemotePlaylist,
        tasks: List<PreviewTask>
    ) {

        val needDownload = mutableListOf<PreviewTask>()
        val reference = mutableListOf<PlaylistCrossRef>()

        withContext(Dispatchers.IO) {

            //TODO
            // Проверять что такой плейлист уже есть и обновлять его
            val newLocalId = Id(
                async {
                    val localId = dao.checkLocalPlaylistId(playlist.remoteId)

                    return@async localId ?: dao.addPlaylist(
                        Playlist(
                            remoteId = playlist.remoteId,
                            title = playlist.title,
                            capacity = playlist.capacity,
                            description = playlist.description
                        )
                    )
                }.await()
            )

            for (task in tasks) {
                if (dao.checkIfTaskExist(task.remoteId.value)) {
                    val localId = dao.taskLocalIdByRemote(task.remoteId.value)
                    reference.add(PlaylistCrossRef(newLocalId, Id(localId)))
                } else {
                    needDownload.add(task)
                }
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

            val newWordsReferences = ids.map {
                PlaylistCrossRef(newLocalId, it)
            }

            launch { dao.addMultipleCrossRef(reference + newWordsReferences) }
        }

    }

    suspend fun loadTasks(localId: List<Id>) {

        val remoteIds = localId.map {
            dao.remoteId(it)
        }

        val tasks = mutableListOf<MyTask>()
        val stats = mutableListOf<Statistics>()


        remoteIds.forEachIndexed { i, remoteId ->
            val requested = webSource.loadTask(remoteId)
            if (requested is RequestResult.TaskDownload.FullTask) {
                val task =
                    FormatToMyTaskMapper.initialStringToWord(requested.formatedData, requested.type)
                        .copy(
                            id = localId[i],
                            remoteId = remoteId,
                            isLoaded = true
                        )
                tasks.add(task)
                val stat = Statistics(
                    displayableText = requested.preview,
                    type = requested.type,
                    taskId = Id(-1)
                )
                stats.add(stat)
            }
        }

        val ids = dao.addNewTasks(tasks)

        val statsToAdd = mutableListOf<Statistics>()
        stats.forEachIndexed { index, statistics ->
            statsToAdd.add(statistics.copy(taskId = Id(ids[index])))
        }

        dao.addStats(statsToAdd)
    }
}