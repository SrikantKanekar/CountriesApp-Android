package com.example.myapplication.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.database.entity.Token

@Dao
interface TokenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(authToken: TokenDao): Long

    @Query("UPDATE token_table SET token = null WHERE account_pk = :pk")
    suspend fun nullifyToken(pk: Int): Int

    @Query("SELECT * FROM token_table WHERE account_pk = :pk")
    suspend fun searchByPk(pk: Int): Token?
}