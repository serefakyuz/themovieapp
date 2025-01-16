package com.serefa.themovieapp.feature.detail

import androidx.lifecycle.viewModelScope
import com.serefa.themovieapp.data.model.movie.detail.MovieDetailResponse
import com.serefa.themovieapp.data.repository.doOnFailure
import com.serefa.themovieapp.data.repository.doOnLoading
import com.serefa.themovieapp.data.repository.doOnSuccess
import com.serefa.themovieapp.data.repository.movie.MovieRepositoryImpl
import com.serefa.themovieapp.data.repository.movie.usecase.MovieDetailUseCase
import com.serefa.themovieapp.feature.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieDetailUseCase: MovieDetailUseCase,
    private val movieDetailRepositoryImpl: MovieRepositoryImpl) :
    BaseViewModel() {

    private val _movieDetail = MutableStateFlow(MovieDetailResponse.EMPTY_INSTANCE)
    val movieDetail get() = _movieDetail.asStateFlow()

    fun getMovieDetail(movieId: String) = viewModelScope.launch {
        movieDetailUseCase(movieId)
            .doOnSuccess {
                _movieDetail.value = it
            }
            .doOnFailure {
            }
            .doOnLoading {
            }.collect()
    }
}