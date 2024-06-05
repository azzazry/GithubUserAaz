package com.dicoding.githubuseraaz.data.retrofit

import com.dicoding.githubuseraaz.BuildConfig.*
import com.dicoding.githubuseraaz.data.response.ItemsItem
import com.dicoding.githubuseraaz.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
//    @Headers("Authorization: token $GITHUB_API_TOKEN")
    fun searchUsers(@Query("q") username: String): Call<UserResponse>

    @GET("users/{username}")
//    @Headers("Authorization: token $GITHUB_API_TOKEN")
    fun getDetailUser(@Path("username") username: String): Call<ItemsItem>

    @GET("users/{username}/followers")
//    @Headers("Authorization: token $GITHUB_API_TOKEN")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
//    @Headers("Authorization: token $GITHUB_API_TOKEN")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}