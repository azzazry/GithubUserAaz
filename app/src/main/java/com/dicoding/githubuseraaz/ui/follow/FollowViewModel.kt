package com.dicoding.githubuseraaz.ui.follow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuseraaz.data.response.ItemsItem
import com.dicoding.githubuseraaz.data.services.UserObject

class FollowViewModel(private val userRepository: UserObject) : ViewModel() {

    constructor() : this(UserObject) {
    }

    private val _followList = MutableLiveData<List<ItemsItem>>()
    val followList: LiveData<List<ItemsItem>> get() = _followList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchFollowers(username: String) {
        _isLoading.postValue(true)
        userRepository.getFollowers(username) { users, error ->
            _isLoading.postValue(false)
            if (error != null) {
                _errorMessage.postValue("Gagal memuat daftar pengguna yang diikuti: $error")
            } else {
                _followList.postValue(users)
            }
        }
    }

    fun fetchFollowing(username: String) {
        _isLoading.postValue(true)
        userRepository.getFollowing(username) { users, error ->
            _isLoading.postValue(false)
            if (error != null) {
                _errorMessage.postValue("Gagal memuat daftar pengguna yang diikuti: $error")
            } else {
                _followList.postValue(users)
            }
        }
    }

    private fun fetchUsers(username: String, callback: (List<ItemsItem>?, String?) -> Unit) {
        _isLoading.postValue(true)
        userRepository.getFollowers(username) { users, error ->
            _isLoading.postValue(false)
            callback(users, error)
        }
    }


}