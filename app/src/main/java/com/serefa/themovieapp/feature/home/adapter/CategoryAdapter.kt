package com.serefa.themovieapp.feature.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.serefa.themovieapp.data.model.movie.Movie
import com.serefa.themovieapp.data.model.movie.local.CategoryItem
import com.serefa.themovieapp.databinding.ItemCategoryBinding

class CategoryAdapter(val onMovieClick: (Movie) -> Unit) : ListAdapter<CategoryItem, RecyclerView.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CategoryViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CategoryViewHolder).bind(getItem(position))
    }

    object DiffCallback : DiffUtil.ItemCallback<CategoryItem>() {
        override fun areItemsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
            return oldItem.category == newItem.category && oldItem.movies.size == newItem.movies.size
        }

        override fun areContentsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean =
            oldItem == newItem
    }

    inner class CategoryViewHolder private constructor(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        constructor(parent: ViewGroup) : this(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

        fun bind(item: CategoryItem) {
            binding.apply {
                textViewCategoryTitle.text =
                    binding.root.context.getString(item.getCategoryNameResId())
                val adapter = MovieAdapter(onMovieClick)
                recyclerViewMovies.adapter = adapter
                adapter.submitList(item.movies)

            }
        }
    }

}