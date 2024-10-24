package com.example.russian.back.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.russian.back.data.dao.GameDao
import com.example.russian.back.data.dao.LoadingDao
import com.example.russian.back.data.dao.StatsDao
import com.example.russian.back.data.entity.MyTask
import com.example.russian.back.data.entity.PartOfTask
import com.example.russian.back.data.entity.SpellingVariant
import com.example.russian.back.data.entity.Statistics
import com.example.russian.back.data.entity.TaskData
import com.example.russian.back.data.entity.playlist.Playlist
import com.example.russian.back.data.entity.playlist.PlaylistCrossRef
import com.example.russian.back.data.entity.playlist.PlaylistPositions

@Database(
    entities = [
        MyTask::class,
        Playlist::class,
        PlaylistCrossRef::class,
        PartOfTask::class,
        TaskData::class,
        Statistics::class,
        PlaylistPositions::class,
        SpellingVariant::class
    ],
    version = 8
)
abstract class MyTaskPlaylistDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao
    abstract fun loadingDao(): LoadingDao

    abstract fun statsDao(): StatsDao

    companion object {
        @Volatile
        private var INSTANCE: MyTaskPlaylistDatabase? = null

        fun getWordPlaylistDB(context: Context): MyTaskPlaylistDatabase {
            return INSTANCE ?: synchronized(
                this
            ) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyTaskPlaylistDatabase::class.java,
                    "word-playlist-db"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}