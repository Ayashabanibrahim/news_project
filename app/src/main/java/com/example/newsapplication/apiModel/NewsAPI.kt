package com.example.newsapplication.apiModel

import com.example.newsapplication.apiModel.Constants.Companion.APIKEY
import com.example.newsapplication.dataModel.NewsResponse
import com.example.newsapplication.dataModel.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("sources")
    fun getSources(@Query("category") category:String,@Query("apikey") apiKey:String=APIKEY,@Query("Language")language:String="en"):Call<SourcesResponse>
   @GET("everything")
   fun getResults(@Query("sources") source:String  ,@Query("q") query:String,@Query("apikey") apiKey:String= APIKEY ):Call<NewsResponse>
}