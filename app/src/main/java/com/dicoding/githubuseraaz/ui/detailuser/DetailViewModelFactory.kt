package com.dicoding.githubuseraaz.ui.detailuser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubuseraaz.database.FavoriteUserDao

class DetailViewModelFactory(private val favoriteUserDao: FavoriteUserDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(favoriteUserDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}