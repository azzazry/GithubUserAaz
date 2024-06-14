package com.dicoding.githubuseraaz.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_users")
data class FavoriteUser(
    @PrimaryKey val username: String,
    val avatarUrl: String?
)