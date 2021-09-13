package com.example.myapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.database.dao.TokenDao
import com.example.myapplication.database.dao.UserDao
import com.example.myapplication.database.entity.Token
import com.example.myapplication.database.entity.User

@Database(entities = [Token::class, User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getTokenDao(): TokenDao

    abstract fun getUserDao(): UserDao

    companion object {
        const val DATABASE_NAME: String = "DATABASE_NAME"
    }
}