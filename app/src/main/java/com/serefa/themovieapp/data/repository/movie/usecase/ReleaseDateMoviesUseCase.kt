package com.serefa.themovieapp.data.repository.movie.usecase

import com.serefa.themovieapp.data.repository.movie.MovieRepository
import javax.inject.Inject

class ReleaseDateMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(page: Int) = movieRepository.getMovieList(MovieRepository.FILTER_RELEASE_DATE, page)
}