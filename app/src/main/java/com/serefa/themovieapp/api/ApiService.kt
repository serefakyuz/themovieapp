package com.serefa.themovieapp.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/discover/movie")
    suspend fun getCharacterList(
        @Query("sort_by") sortBy: String,
        @Query("page") page: Int
    ): Response<Any>
}