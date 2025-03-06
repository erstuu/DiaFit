package com.health.diafit.data.di

import android.content.Context
import androidx.room.Room
import com.health.diafit.BuildConfig
import com.health.diafit.data.local.DiafitDatabase
import com.health.diafit.data.local.HistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
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
}
