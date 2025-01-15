package com.serefa.themovieapp.api

import com.serefa.themovieapp.data.model.MovieResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/3/discover/movie")
    suspend fun getCharacterList(
        @Query("sort_by") sortBy: String,
        @Query("page") page: Int
    ): Response<MovieResponseModel>


    @GET("/3/movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): Response<MovieResponseModel>
}