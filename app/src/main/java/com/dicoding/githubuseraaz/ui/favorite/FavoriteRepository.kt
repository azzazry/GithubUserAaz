package com.dicoding.githubuseraaz.ui.favorite

import android.content.Context
import androidx.lifecycle.LiveData
import com.dicoding.githubuseraaz.database.AppDatabase
import com.dicoding.githubuseraaz.database.FavoriteUser
import com.dicoding.githubuseraaz.database.FavoriteUserDao

class FavoriteRepository(context: Context) {
    private val favoriteUserDao: FavoriteUserDao =
        AppDatabase.getInstance(context).favoriteUserDao()

    fun getFavoriteUsers(): LiveData<List<FavoriteUser>> {
        return favoriteUserDao.getFavoriteUsers()
    }
}