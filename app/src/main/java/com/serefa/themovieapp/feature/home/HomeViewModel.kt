package com.serefa.themovieapp.feature.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.serefa.themovieapp.data.model.movie.local.Category
import com.serefa.themovieapp.data.model.movie.local.CategoryItem
import com.serefa.themovieapp.data.model.movie.Movie
import com.serefa.themovieapp.data.repository.doOnFailure
import com.serefa.themovieapp.data.repository.doOnLoading
import com.serefa.themovieapp.data.repository.doOnSuccess
import com.serefa.themovieapp.data.repository.movie.usecase.PopularMoviesUseCase
import com.serefa.themovieapp.data.repository.movie.usecase.ReleaseDateMoviesUseCase
import com.serefa.themovieapp.data.repository.movie.usecase.RevenueMoviesUseCase
import com.serefa.themovieapp.data.repository.movie.usecase.TopRatedMoviesUseCase
import com.serefa.themovieapp.feature.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val popularMoviesUseCase: PopularMoviesUseCase,
    private val topRatedMoviesUseCase: TopRatedMoviesUseCase,
    private val revenueMoviesUseCase: RevenueMoviesUseCase,
    private val releaseDateMoviesUseCase: ReleaseDateMoviesUseCase
): BaseViewModel() {

    private val categoryMap: Map<Category, CategoryItem> = mapOf(
        Pair(Category.POPULAR, CategoryItem(category = Category.POPULAR)),
        Pair(Category.TOP_RATED, CategoryItem(category = Category.TOP_RATED)),
        Pair(Category.REVENUE, CategoryItem(category = Category.REVENUE)),
        Pair(Category.RELEASE_DATE, CategoryItem(category = Category.RELEASE_DATE))
    )

    private val _popularMovies = MutableStateFlow(emptyList<Movie>())
    val popularMovies get() = _popularMovies.asStateFlow()
    private val _topRatedMovies = MutableStateFlow(emptyList<Movie>())
    val topRatedMovies get() = _topRatedMovies.asStateFlow()
    private val _revenueMovies = MutableStateFlow(emptyList<Movie>())
    val revenueMovies get() = _revenueMovies.asStateFlow()
    private val _releaseDateMovies = MutableStateFlow(emptyList<Movie>())
    val releaseDateMovies get() = _releaseDateMovies.asStateFlow()

    fun getCategories() = categoryMap.values.toList()

    private fun getPopularMovies(page: Int) = viewModelScope.launch{
        popularMoviesUseCase(page)
            .doOnSuccess {
                val category = categoryMap[Category.POPULAR]
                category?.totalPages = it.totalPages?:0
                category?.movies?.addAll(it.results)
                _popularMovies.value +=  it.results
            }
            .doOnFailure {
            }
            .doOnLoading {
            }
            .collect()
    }
    private fun getTopRatedMovies(page: Int) = viewModelScope.launch{
        topRatedMoviesUseCase(page)
            .doOnSuccess {
                val category = categoryMap[Category.TOP_RATED]
                category?.totalPages = it.totalPages?:0
                category?.movies?.addAll(it.results)
                _topRatedMovies.value = it.results
            }
            .doOnFailure {
            }
            .doOnLoading {
            }
            .collect()
    }
    private fun getRevenueMovies(page: Int) = viewModelScope.launch{
        revenueMoviesUseCase(page)
            .doOnSuccess {
                val category = categoryMap[Category.REVENUE]
                category?.totalPages = it.totalPages?:0
                category?.movies?.addAll(it.results)
                _revenueMovies.value = it.results
            }
            .doOnFailure {
            }
            .doOnLoading {
            }
            .collect()
    }
    private fun getReleaseDateMovies(page: Int) = viewModelScope.launch{
        releaseDateMoviesUseCase(page)
            .doOnSuccess {
                val category = categoryMap[Category.RELEASE_DATE]
                category?.totalPages = it.totalPages?:0
                category?.movies?.addAll(it.results)
                _releaseDateMovies.value = it.results
            }
            .doOnFailure {
            }
            .doOnLoading {
            }
            .collect()
    }
    fun loadMore(category: CategoryItem){
        category.page++
        if(category.page > category.totalPages) return
        when(category.category){
            Category.POPULAR -> getPopularMovies(category.page)
            Category.TOP_RATED -> getTopRatedMovies(category.page)
            Category.REVENUE -> getRevenueMovies(category.page)
            Category.RELEASE_DATE -> getReleaseDateMovies(category.page)
            else -> Log.e("HomeViewModel", "Unknown category")

        }
    }

    init {
        viewModelScope.launch {
            coroutineScope {
                launch {
                    getPopularMovies(1)
                    getTopRatedMovies(1)
                    getRevenueMovies(1)
                    getReleaseDateMovies(1)
                }
            }
        }
    }

}