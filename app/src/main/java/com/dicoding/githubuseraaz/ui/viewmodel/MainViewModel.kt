package com.dicoding.githubuseraaz.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuseraaz.data.response.ItemsItem
import com.dicoding.githubuseraaz.data.response.UserResponse
import com.dicoding.githubuseraaz.data.retrofit.ApiConfig
import com.dicoding.githubuseraaz.data.retrofit.UserObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val _userList = MutableLiveData<List<ItemsItem>>()
    val userList: LiveData<List<ItemsItem>> = _userList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _detailUser = MutableLiveData<ItemsItem?>()
    val detailUser: LiveData<ItemsItem?> = _detailUser

    private var searchQuery: String? = null

    fun setSearchQuery(q: String) {
        Log.d("MainViewModel", "Search query set to $q")
        searchQuery = q
    }

    fun getSearchQuery(): String? {
        return searchQuery
    }

    fun fetchUserDetail(username: String) {
        UserObject.getUserDetail(username) { detailUser ->
            _detailUser.postValue(detailUser)
        }
    }

    fun fetchData(username: String) {
        val query = getSearchQuery()
        val APIservice = ApiConfig.getApiService()
        val call = APIservice.searchUsers(query ?: username)

        call.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                if (response.isSuccessful) {
                    val userList = response.body()?.items ?: emptyList()
                    Log.d("API Response", "User List: $userList")
                    _userList.postValue(userList as List<ItemsItem>?)
                } else {
                    _errorMessage.postValue("Gagal memuat daftar pengguna")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _errorMessage.postValue("Error: ${t.message}")
            }
        })
    }
}