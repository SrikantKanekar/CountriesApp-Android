package com.example.myapplication.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.myapplication.database.entity.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table WHERE email = :email")
    suspend fun searchByEmail(email: String): User?

    @Query("SELECT * FROM user_table WHERE pk = :pk")
    suspend fun searchByPk(pk: Int): User

    @Insert(onConflict = REPLACE)
    suspend fun insertAndReplace(accountProperties: User): Long

    @Insert(onConflict = IGNORE)
    suspend fun insertOrIgnore(accountProperties: User): Long

    @Query("UPDATE user_table SET email = :email, name = :name WHERE pk = :pk")
    suspend fun updateAccountProperties(pk: Int, email: String, name: String)
}