package com.rnm.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.rnm.data.local.AppDatabase
import com.rnm.data.local.dao.CardDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) : AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences = context.getSharedPreferences(
        SHARED_PREFERENCES, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideCharacterEntityDao(appDatabase: AppDatabase): CardDao = appDatabase.CardDao()
}

const val SHARED_PREFERENCES = "sharedPreferences"