package com.tlw.streakwolf.di

import android.content.Context
import androidx.room3.Room
import com.tlw.streakwolf.data.local.StreakWolfDatabase
import com.tlw.streakwolf.data.local.dao.CompletionDao
import com.tlw.streakwolf.data.local.dao.HabitDao
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
    fun provideStreakWolfDatabase(@ApplicationContext appContext: Context): StreakWolfDatabase {
        return Room.databaseBuilder(
            appContext,
            StreakWolfDatabase::class.java,
            StreakWolfDatabase.STREAK_WOLF_DB
        ).build()
    }

    @Provides
    @Singleton
    fun provideHabitDao(db: StreakWolfDatabase): HabitDao {
        return db.habitDao()
    }

    @Provides
    @Singleton
    fun provideCompletionDao(db: StreakWolfDatabase): CompletionDao {
        return db.completionDao()
    }
}