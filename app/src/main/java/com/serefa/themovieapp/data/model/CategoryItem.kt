package com.serefa.themovieapp.data.model

import com.serefa.themovieapp.R

data class CategoryItem(
    var page: Int = 1,
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
