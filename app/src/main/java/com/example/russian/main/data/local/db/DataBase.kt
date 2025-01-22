package com.example.russian.main.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.russian.main.data.local.db.entity.Answer
import com.example.russian.game.back.data.dao.GameDao
import com.example.russian.game.back.data.dao.LoadingDao
import com.example.russian.game.back.data.dao.StatsDao
import com.example.russian.game.back.data.entity.MyTask
import com.example.russian.game.back.data.entity.PartOfTask
import com.example.russian.game.back.data.entity.SpellingVariant
import com.example.russian.game.back.data.entity.Statistics
import com.example.russian.game.back.data.entity.TaskData
import com.example.russian.game.back.data.entity.playlist.Playlist
import com.example.russian.game.back.data.entity.playlist.PlaylistCrossRef
import com.example.russian.game.back.data.entity.playlist.PlaylistPositions


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
    version = 3,
    exportSchema = false
)
abstract class TaskDataBase: RoomDatabase() {

    abstract fun gameDao(): GameDao
    abstract fun loadingDao(): LoadingDao
    abstract fun statsDao(): StatsDao

}

