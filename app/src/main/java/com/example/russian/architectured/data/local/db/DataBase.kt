package com.example.russian.architectured.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.russian.architectured.data.local.db.entity.Answer
import com.example.russian.main.back.data.dao.GameDao
import com.example.russian.main.back.data.dao.LoadingDao
import com.example.russian.main.back.data.dao.StatsDao
import com.example.russian.main.back.data.entity.MyTask
import com.example.russian.main.back.data.entity.PartOfTask
import com.example.russian.main.back.data.entity.SpellingVariant
import com.example.russian.main.back.data.entity.Statistics
import com.example.russian.main.back.data.entity.TaskData
import com.example.russian.main.back.data.entity.playlist.Playlist
import com.example.russian.main.back.data.entity.playlist.PlaylistCrossRef
import com.example.russian.main.back.data.entity.playlist.PlaylistPositions


@Database(
    entities = [
        MyTask::class,
        Playlist::class,
        PlaylistCrossRef::class,
        PartOfTask::class,
        TaskData::class,
        Statistics::class,
        PlaylistPositions::class,
        SpellingVariant::class,
        Answer::class
    ],
    version = 1
)
abstract class TaskDataBase: RoomDatabase() {

    abstract fun gameDao(): GameDao
    abstract fun loadingDao(): LoadingDao
    abstract fun statsDao(): StatsDao

}

