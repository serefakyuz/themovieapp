package com.serefa.themovieapp.data.repository.movie

import com.serefa.themovieapp.api.ApiService
import com.serefa.themovieapp.data.repository.BaseRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: ApiService
): BaseRepository(), MovieRepository {

    override suspend fun getMovieList(sortBy: String, page: Int) = safeApiCall {
        api.getCharacterList(sortBy, page)
    }

    override suspend fun getMovieDetail(movieId: String) = safeApiCall {
        api.getMovieDetail(movieId)
    }
}