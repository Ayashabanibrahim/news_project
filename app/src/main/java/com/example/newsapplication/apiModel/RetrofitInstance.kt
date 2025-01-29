package com.example.newsapplication.apiModel

import com.example.newsapplication.apiModel.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object{
        val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val apiInterface: NewsAPI by lazy {
            retrofit.create(NewsAPI::class.java)
        }

    }
}