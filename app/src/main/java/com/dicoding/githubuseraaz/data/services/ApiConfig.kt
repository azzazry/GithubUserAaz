package com.dicoding.githubuseraaz.data.services

import com.dicoding.githubuseraaz.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private const val BASE_URL = BuildConfig.BASE_URL
    private val debug = BuildConfig.DEBUG
    private const val GITHUB_API = BuildConfig.GITHUB_API_TOKEN
    fun getApiService(): ApiService {
        val loggingInterceptor = if (debug){
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor{chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", " token $GITHUB_API")
                    .build()
                chain.proceed(newRequest)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}
