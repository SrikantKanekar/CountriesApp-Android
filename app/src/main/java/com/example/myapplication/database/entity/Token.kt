package com.example.myapplication.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "token_table",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["pk"],
            childColumns = ["account_pk"],
            onDelete = CASCADE
        )
    ]
)
data class Token(
    @PrimaryKey
    var account_pk: Int? = -1,
    var token: String? = null
)