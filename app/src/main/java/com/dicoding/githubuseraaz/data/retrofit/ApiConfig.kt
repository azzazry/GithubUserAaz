package com.dicoding.githubuseraaz.data.retrofit

import com.dicoding.githubuseraaz.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private val baseUrl = BuildConfig.BASE_URL
    private val debug = BuildConfig.DEBUG
    private val githubApi = BuildConfig.GITHUB_API_TOKEN
    fun getApiService(): ApiService {
        // loggingHttpInterceptor
        val loggingInterceptor = if (debug){
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        // client variable
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor{chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", " token $githubApi")
                    .build()
                chain.proceed(newRequest)
            }
            .build()

        // retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}
