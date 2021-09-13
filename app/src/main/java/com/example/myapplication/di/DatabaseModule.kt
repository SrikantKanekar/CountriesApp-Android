package com.example.myapplication.di

import androidx.room.Room
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.AppDatabase.Companion.DATABASE_NAME
import com.example.myapplication.database.dao.TokenDao
import com.example.myapplication.database.dao.UserDao
import com.example.myapplication.presentation.ui.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideAppDatabase(app: BaseApplication): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideTokenDao(db: AppDatabase): TokenDao {
        return db.getTokenDao()
    }

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao {
        return db.getUserDao()
    }
}