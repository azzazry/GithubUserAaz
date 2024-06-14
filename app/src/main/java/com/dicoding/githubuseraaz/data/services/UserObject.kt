package com.dicoding.githubuseraaz.data.services

import com.dicoding.githubuseraaz.data.response.ItemsItem
import com.dicoding.githubuseraaz.data.response.ItemsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserObject {
    private val ApiService = ApiConfig.getApiService()

    fun getUserDetail(username: String, callback: (ItemsResponse?) -> Unit) {
        ApiService.getDetailUser(username).enqueue(object : Callback<ItemsResponse> {
            override fun onResponse(
                call: Call<ItemsResponse>, response: Response<ItemsResponse>
            ) {
                if (response.isSuccessful) {
                    val userDetail = response.body()
                    callback(userDetail)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<ItemsResponse>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun getFollowers(username: String, callback: (List<ItemsItem>?, String?) -> Unit) {
        ApiService.getFollowers(username).enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>, response: Response<List<ItemsItem>>
            ) {
                if (response.isSuccessful) {
                    val followers = response.body()
                    callback(followers, null)
                } else {
                    callback(null, "Failed to fetch followers")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                callback(null, "Error: ${t.message}")
            }
        })
    }

    fun getFollowing(username: String, callback: (List<ItemsItem>?, String?) -> Unit) {
        ApiService.getFollowing(username).enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>, response: Response<List<ItemsItem>>
            ) {
                if (response.isSuccessful) {
                    val following = response.body()
                    callback(following, null)
                } else {
                    callback(null, "Failed to fetch following")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                callback(null, "Error: ${t.message}")
            }
        })
    }
}
