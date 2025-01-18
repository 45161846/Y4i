package com.example.russian.architectured.data.local.db

import android.content.Context
import android.content.SharedPreferences
import android.content.res.AssetManager
import androidx.room.Room
import com.example.russian.architectured.data.local.db.repo.LocalStatsRepository
import com.example.russian.architectured.data.local.db.repo.StatsRepositoryApi
import com.example.russian.architectured.settings.Settings
import com.example.russian.architectured.util.SHARED_PREFERENCES_KEY
import com.example.russian.main.back.data.dao.GameDao
import com.example.russian.main.back.data.dao.LoadingDao
import com.example.russian.main.back.data.dao.StatsDao
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
    fun provideLoadingDao(database: TaskDataBase): LoadingDao = database.loadingDao()

    @Provides
    fun provideAssets(@ApplicationContext context: Context): AssetManager{
        return context.assets
    }

    @Provides
    fun provideStatsDao(database: TaskDataBase): StatsDao = database.statsDao()


    @Provides
    fun provideGame(database: TaskDataBase): GameDao = database.gameDao()

}

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule{

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences{
        return context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
    }

    @Provides
    fun provideSettings(@ApplicationContext context: Context) = Settings(
        provideSharedPreferences(context)
    )

}