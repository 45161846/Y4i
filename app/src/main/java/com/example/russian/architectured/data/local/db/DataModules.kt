package com.example.russian.architectured.data.local.db

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.russian.architectured.data.local.db.dao.StatsDao
import com.example.russian.architectured.data.local.db.dao.TaskDao
import com.example.russian.architectured.data.local.db.repo.LocalStatsRepository
import com.example.russian.architectured.data.local.db.repo.StatsRepositoryApi
import com.example.russian.architectured.util.SHARED_PREFERENCES_KEY
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{

    @Singleton
    @Binds
    abstract fun bindTaskRepository(repository: LocalStatsRepository): StatsRepositoryApi

}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule{

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): TaskDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            TaskDataBase::class.java,
            "Tasks.db"
        ).build()
    }

    @Provides
    fun provideTaskDao(database: TaskDataBase): TaskDao = database.taskDao()

    @Provides
    fun provideStatsDao(database: TaskDataBase): StatsDao = database.statDao()

}

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule{

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences{
        return context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
    }

}