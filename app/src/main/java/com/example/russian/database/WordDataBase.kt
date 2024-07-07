package com.example.russian.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.russian.architecture.data.dao.WordDao
import com.example.russian.architecture.data.entity.Word


@Database(entities = [Word::class], version = 16)
abstract class WordDataBase: RoomDatabase() {

    abstract fun wordDao(): WordDao

    companion object{
        @Volatile
        private var INSTANCE: WordDataBase?= null

        fun getDatabase(
            context: Context
            ): WordDataBase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordDataBase::class.java,
                    "word_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }

}