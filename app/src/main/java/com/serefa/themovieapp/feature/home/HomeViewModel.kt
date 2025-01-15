package com.serefa.themovieapp.feature.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.serefa.themovieapp.data.model.Movie
import com.serefa.themovieapp.data.repository.doOnFailure
import com.serefa.themovieapp.data.repository.doOnLoading
import com.serefa.themovieapp.data.repository.doOnSuccess
import com.serefa.themovieapp.data.repository.movie.usecase.PopularMoviesUseCase
import com.serefa.themovieapp.feature.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val popularMoviesUseCase: PopularMoviesUseCase
): BaseViewModel() {


    private val _popularMovies = MutableStateFlow(emptyList<Movie>())
    val popularMovies get() = _popularMovies.asStateFlow()

    fun getPopularMovies(page: Int) = viewModelScope.launch{
        popularMoviesUseCase(page)
            .doOnSuccess {
                _popularMovies.value = it.results
                Log.e("TAGTAGTAG", "getPopularMovies: " + it.results?.size )
            }
            .doOnFailure {
                Log.e("TAGTAGTAG", "getPopularMovies - FAIL: $it")
            }
            .doOnLoading {
                Log.e("TAGTAGTAG", "getPopularMovies - LOADING:")
            }
            .collect()
    }

    init {
        getPopularMovies(1)
    }

}