package com.health.diafit.data.di

import android.content.Context
import androidx.room.Room
import com.health.diafit.data.local.DiafitDatabase
import com.health.diafit.data.local.HistoryDao
import com.health.diafit.data.local.UserPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDiafitDatabase(@ApplicationContext context: Context): DiafitDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            DiafitDatabase::class.java,
            "diafit_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideHistoryDao(database: DiafitDatabase): HistoryDao {
        return database.historyDao()
    }

    @Provides
    @Singleton
    fun provideUserPreference(@ApplicationContext context: Context): UserPreference {
        return UserPreference(context)
    }
}
