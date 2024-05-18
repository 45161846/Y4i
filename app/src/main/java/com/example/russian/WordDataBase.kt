package com.example.russian

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Word::class], version = 8)
abstract class WordDataBase: RoomDatabase() {

    abstract fun wordDao(): WordDao

    companion object{
        @Volatile
        private var INSTANCE: WordDataBase?= null

        fun getDatabase(
            context: Context
            ): WordDataBase{
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