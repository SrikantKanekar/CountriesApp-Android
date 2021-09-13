package com.example.myapplication.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = false)
    val pk: Int,
    val name: String,
    val mobile: String,
    val email: String,
    val password: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (pk != other.pk) return false

        return true
    }

    override fun hashCode(): Int {
        return pk
    }
}
