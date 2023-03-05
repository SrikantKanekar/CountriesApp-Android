package com.example.myapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertAndReplace(user: User): Long

    @Query("SELECT * FROM user_table WHERE email = :email")
    suspend fun searchByEmail(email: String): User?

    @Query("DELETE FROM user_table WHERE email = :email")
    suspend fun deleteByEmail(email: String)
}