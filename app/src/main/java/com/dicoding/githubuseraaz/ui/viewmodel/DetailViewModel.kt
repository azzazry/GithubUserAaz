package com.dicoding.githubuseraaz.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuseraaz.data.response.ItemsItem
import com.dicoding.githubuseraaz.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val ApiService = ApiConfig.getApiService()
    private val _detailUser = MutableLiveData<ItemsItem?>()
    val detailUser: LiveData<ItemsItem?> = _detailUser
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun fetchUserDetail(username: String) {
        ApiService.getDetailUser(username).enqueue(object : Callback<ItemsItem> {
            override fun onResponse(call: Call<ItemsItem>, response: Response<ItemsItem>) {
                if (response.isSuccessful) {
                    val detailUser = response.body()
                    _detailUser.postValue(detailUser)
                } else {
                    _errorMessage.postValue("Gagal memuat daftar pengguna")
                }
            }

            override fun onFailure(call: Call<ItemsItem>, t: Throwable) {
                _errorMessage.postValue("Error: ${t.message}")
            }
        })
    }
}