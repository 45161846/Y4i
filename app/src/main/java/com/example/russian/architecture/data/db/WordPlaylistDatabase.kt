package com.example.russian.architecture.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.russian.architecture.data.entity.Playlist
import com.example.russian.architecture.data.dao.PlaylistDao
import com.example.russian.architecture.data.entity.PlaylistWordCrossRef
import com.example.russian.architecture.data.dao.PlaylistWordCrossRefDao
import com.example.russian.architecture.data.entity.Word
import com.example.russian.architecture.data.dao.WordDao


@Database(
    entities = [
        Word::class,
        Playlist::class,
        PlaylistWordCrossRef::class
    ],
    version = 2
)
abstract class WordPlaylistDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao

    abstract fun playlistDao(): PlaylistDao

    abstract fun crossRefDao(): PlaylistWordCrossRefDao

    companion object{
        @Volatile
        private var INSTANCE: WordPlaylistDatabase? = null

        fun getWordPlaylistDB(context: Context): WordPlaylistDatabase {
            return INSTANCE ?: synchronized(
                this
            ){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordPlaylistDatabase::class.java,
                    "word-playlist-db"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                instance
            }
        }

    }

}