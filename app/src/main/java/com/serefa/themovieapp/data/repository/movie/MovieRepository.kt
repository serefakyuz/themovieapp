package com.serefa.themovieapp.data.repository.movie

import com.serefa.themovieapp.data.model.movie.MovieResponseModel
import com.serefa.themovieapp.data.model.movie.detail.MovieDetailResponse
import kotlinx.coroutines.flow.Flow
import com.serefa.themovieapp.data.repository.Result

interface MovieRepository {
    companion object{
        const val FILTER_POPULAR = "popularity.desc"
        const val FILTER_TOP_RATED = "vote_average.desc"
        const val FILTER_REVENUE = "revenue.desc"
        const val FILTER_RELEASE_DATE = "release_date.desc"
    }

    suspend fun getMovieList(sortBy: String, page: Int): Flow<Result<MovieResponseModel>>

    suspend fun getMovieDetail(movieId: String): Flow<Result<MovieDetailResponse>>
}