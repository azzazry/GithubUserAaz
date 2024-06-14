package com.dicoding.githubuseraaz.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToFavorite(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM favorite_users WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser?>

    @Query("SELECT * FROM favorite_users")
    fun getFavoriteUsers(): LiveData<List<FavoriteUser>>

    @Query("DELETE FROM favorite_users WHERE username = :username")
    suspend fun removeFromFavorite(username: String)

    @Query("SELECT count(*) FROM favorite_users WHERE username = :username")
    suspend fun checkUser(username: String): Int
}