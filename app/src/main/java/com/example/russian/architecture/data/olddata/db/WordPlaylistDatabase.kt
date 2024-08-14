package com.example.russian.architecture.data.olddata.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.russian.architecture.data.olddata.entity.Playlist
import com.example.russian.architecture.data.olddata.dao.PlaylistDao
import com.example.russian.architecture.data.olddata.entity.PlaylistWordCrossRef
import com.example.russian.architecture.data.olddata.dao.PlaylistWordCrossRefDao
import com.example.russian.architecture.data.olddata.dao.SpellingDao
import com.example.russian.architecture.data.olddata.entity.Word
import com.example.russian.architecture.data.olddata.dao.WordDao
import com.example.russian.architecture.data.olddata.entity.Spelling


@Database(
    entities = [
        Word::class,
        Playlist::class,
        PlaylistWordCrossRef::class,
        Spelling::class
    ],
    version = 3
)
abstract class WordPlaylistDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao

    abstract fun playlistDao(): PlaylistDao

    abstract fun crossRefDao(): PlaylistWordCrossRefDao

    abstract fun spellingDao(): SpellingDao

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