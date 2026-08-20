package com.tlw.streakwolf.di

import com.tlw.streakwolf.data.local.dao.CompletionDao
import com.tlw.streakwolf.data.local.dao.HabitDao
import com.tlw.streakwolf.data.repository.HabitRepositoryImpl
import com.tlw.streakwolf.domain.repository.HabitRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideHabitRepo(habitDao: HabitDao, completionDao: CompletionDao): HabitRepository {
        return HabitRepositoryImpl(habitDao, completionDao)
    }
}