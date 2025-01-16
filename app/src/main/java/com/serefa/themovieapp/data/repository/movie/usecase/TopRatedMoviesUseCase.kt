package com.serefa.themovieapp.data.repository.movie.usecase

import com.serefa.themovieapp.data.repository.movie.MovieRepository
import javax.inject.Inject

class TopRatedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(page: Int) = movieRepository.getMovieList(MovieRepository.FILTER_TOP_RATED, page)
}