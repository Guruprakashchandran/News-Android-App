package com.example.newsapp.interfaces

import com.example.newsapp.dataclasses.Details
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsInterface {

    @GET("v4/articles/")
    suspend fun getArticles(
        @Query("format") format: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<Details>

}