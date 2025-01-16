package com.serefa.themovieapp.data.repository.movie.usecase

import com.serefa.themovieapp.data.repository.movie.MovieRepository
import javax.inject.Inject

class MovieDetailUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(videoId: String) = movieRepository.getMovieDetail(videoId)
}