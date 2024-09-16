package com.example.russian.back.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.russian.back.data.dao.GameDao
import com.example.russian.back.data.dao.LoadingDao
import com.example.russian.back.data.dao.StatsDao
import com.example.russian.back.data.entity.NewWord
import com.example.russian.back.data.entity.Spelling
import com.example.russian.back.data.entity.Statistics
import com.example.russian.back.data.entity.TaskData
import com.example.russian.back.data.entity.playlist.Playlist
import com.example.russian.back.data.entity.playlist.PlaylistCrossRef

@Database(
    entities = [
        NewWord::class,
        Playlist::class,
        PlaylistCrossRef::class,
        Spelling::class,
        TaskData::class,
        Statistics::class
    ],
    version = 1
)
abstract class NewWordPlaylistDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao
    abstract fun loadingDao(): LoadingDao

    abstract fun statsDao(): StatsDao
    companion object {
        @Volatile
        private var INSTANCE: NewWordPlaylistDatabase? = null

        fun getWordPlaylistDB(context: Context): NewWordPlaylistDatabase {
            return INSTANCE ?: synchronized(
                this
            ) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewWordPlaylistDatabase::class.java,
                    "word-playlist-db"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                instance
            }
        }

    }

}