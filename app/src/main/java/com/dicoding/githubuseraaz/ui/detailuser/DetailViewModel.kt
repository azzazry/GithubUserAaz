package com.dicoding.githubuseraaz.ui.detailuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.githubuseraaz.data.response.ItemsResponse
import com.dicoding.githubuseraaz.data.services.ApiConfig
import com.dicoding.githubuseraaz.database.FavoriteUser
import com.dicoding.githubuseraaz.database.FavoriteUserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val favoriteUserDao: FavoriteUserDao) : ViewModel() {
    private val apiService = ApiConfig.getApiService()
    private val _userDetail = MutableLiveData<ItemsResponse?>()
    val userDetail: LiveData<ItemsResponse?> = _userDetail
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun fetchUserDetail(username: String) {
        apiService.getDetailUser(username).enqueue(object : Callback<ItemsResponse> {
            override fun onResponse(
                call: Call<ItemsResponse>, response: Response<ItemsResponse>
            ) {
                if (response.isSuccessful) {
                    val detailUser = response.body()
                    _userDetail.postValue(detailUser)
                    detailUser?.login?.let { username ->
                        checkIsFavorite(username)
                    }
                } else {
                    _errorMessage.postValue("Failed to load user details")
                }
            }

            override fun onFailure(call: Call<ItemsResponse>, t: Throwable) {
                _errorMessage.postValue("Error: ${t.message}")
            }
        })
    }

    fun addToFavorites(user: ItemsResponse) {
        val favoriteUser = FavoriteUser(user.login ?: "", user.avatarUrl ?: "")
        viewModelScope.launch(Dispatchers.IO) {
            favoriteUserDao.addToFavorite(favoriteUser)
            setFavoriteStatus(true)
        }
    }


    fun removeFromFavorites(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteUserDao.removeFromFavorite(username)
            setFavoriteStatus(false)
        }
    }


    private fun checkIsFavorite(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val isFavorited = favoriteUserDao.checkUser(username) > 0
            setFavoriteStatus(isFavorited)
            Log.d("FavoriteStatus", "Is favorite: $isFavorited")
        }
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser?> {
        return favoriteUserDao.getFavoriteUserByUsername(username)
    }


    private fun setFavoriteStatus(isFavorite: Boolean) {
        _isFavorite.postValue(isFavorite)
    }
}