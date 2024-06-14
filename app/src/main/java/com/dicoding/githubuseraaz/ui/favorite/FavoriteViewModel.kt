package com.dicoding.githubuseraaz.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubuseraaz.database.FavoriteUser

class FavoriteViewModel(private val repository: FavoriteRepository) : ViewModel() {
    fun getFavoriteUsers(): LiveData<List<FavoriteUser>> {
        return repository.getFavoriteUsers()
    }
}

class FavoriteViewModelFactory(private val repository: FavoriteRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return FavoriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}