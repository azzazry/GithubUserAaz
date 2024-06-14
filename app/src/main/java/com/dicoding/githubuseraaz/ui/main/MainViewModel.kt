package com.dicoding.githubuseraaz.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuseraaz.data.response.ItemsItem
import com.dicoding.githubuseraaz.data.response.ItemsResponse
import com.dicoding.githubuseraaz.data.response.UserResponse
import com.dicoding.githubuseraaz.data.services.ApiConfig
import com.dicoding.githubuseraaz.data.services.UserObject
import com.dicoding.githubuseraaz.utils.SettingPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: SettingPreferences) : ViewModel() {
    private val _userList = MutableLiveData<List<ItemsItem>>()
    val userList: LiveData<List<ItemsItem>> = _userList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _userDetail = MutableLiveData<ItemsResponse?>()
    val userDetail: MutableLiveData<ItemsResponse?> = _userDetail

    private var searchQuery: String? = null

    fun setSearchQuery(query: String) {
        searchQuery = query
    }

    fun getSearchQuery(): String? {
        return searchQuery
    }

    fun fetchUserDetail(username: String) {
        UserObject.getUserDetail(username) { userDetail ->
            _userDetail.postValue(userDetail)
        }
    }

    fun fetchData(username: String) {
        val query = getSearchQuery()
        val apiservice = ApiConfig.getApiService()
        val call = apiservice.searchUsers(query ?: username)

        call.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>, response: Response<UserResponse>
            ) {
                if (response.isSuccessful) {
                    val userList = response.body()?.items ?: emptyList()
                    Log.d("API Response", "User List: $userList")
                    _userList.postValue(userList as List<ItemsItem>?)
                } else {
                    _errorMessage.postValue("Failed to load data")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _errorMessage.postValue("Error: ${t.message}")
            }
        })
    }
}