package com.example.newsapp.data.api

import com.example.newsapp.models.NewsResponce
import com.example.newsapp.ui.utils.Costants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/everything")
    suspend fun getEverything(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey : String = API_KEY
    ): Response<NewsResponce>
    @GET("v2/top-headlines")
    suspend fun getHeadLines(
    @Query("country")  countryCode: String = "ru",
    @Query("page")  page: Int = 1,
    @Query("apiKey") apiKey : String = API_KEY
    ) : Response<NewsResponce>
}