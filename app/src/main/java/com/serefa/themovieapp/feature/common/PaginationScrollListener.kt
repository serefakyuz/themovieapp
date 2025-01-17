package com.serefa.themovieapp.feature.common

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.serefa.themovieapp.data.model.movie.local.CategoryItem

class PaginationScrollListener(
    private val linearLayoutManager: LinearLayoutManager,
    private val loadMore: (CategoryItem) -> Unit): RecyclerView.OnScrollListener() {

    private val VISIBLE_THRESHOLD = 3
    private var loading = false


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dx == 0 && dy == 0){
            return
        }
        val totalItemCount = linearLayoutManager.itemCount
        val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
        if (!loading && totalItemCount <= lastVisibleItem + VISIBLE_THRESHOLD
            && totalItemCount != 0) {
            loadMore(recyclerView.tag as CategoryItem)
            loading = true
        }
    }

    fun setLoaded() {
        loading = false
    }
}