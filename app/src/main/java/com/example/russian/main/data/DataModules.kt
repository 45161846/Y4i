package com.example.russian.main.data

import android.content.Context
import android.content.SharedPreferences
import android.content.res.AssetManager
import androidx.room.Room
import com.example.russian.game.back.data.dao.GameDao
import com.example.russian.game.back.data.dao.LoadingDao
import com.example.russian.game.back.data.dao.StatsDao
import com.example.russian.main.data.local.db.TaskDataBase
import com.example.russian.main.data.local.db.repo.LocalStatsRepository
import com.example.russian.main.data.local.db.repo.StatsRepositoryApi
import com.example.russian.main.data.remote.RemotePlaylistRepository
import com.example.russian.main.data.remote.RemotePlaylistRepositoryAPI
import com.example.russian.main.settings.Settings
import com.example.russian.main.util.SHARED_PREFERENCES_KEY
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

    @Singleton
    @Binds
    abstract fun bindRemoteLoginRepository(repository: RemotePlaylistRepository): RemotePlaylistRepositoryAPI
    
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
        )
            .fallbackToDestructiveMigration()
            .build()
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

@Module
@InstallIn(SingletonComponent::class)
object ToastModule{
    @Provides
    fun provideToastHandler(@ApplicationContext context: Context): ToastHandler{
        return ToastHandler(context)
    }
}