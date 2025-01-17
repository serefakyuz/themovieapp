package com.serefa.themovieapp.data.model.movie.local

import com.serefa.themovieapp.R
import com.serefa.themovieapp.data.model.movie.Movie

data class CategoryItem(
    var page: Int = 1,
    var totalPages: Int = 1,
    val category: Category,
    var movies: MutableList<Movie> = mutableListOf()
){
    fun getCategoryNameResId() = when(category){
        Category.POPULAR -> R.string.categoryPopular
        Category.TOP_RATED -> R.string.categoryTopRated
        Category.REVENUE -> R.string.categoryRevenue
        Category.RELEASE_DATE -> R.string.categoryReleaseDate
        else -> R.string.undefinedCategory
    }
}
