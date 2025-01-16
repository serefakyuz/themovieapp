package com.serefa.themovieapp.feature.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.serefa.themovieapp.data.model.movie.local.ImageQuality
import com.serefa.themovieapp.data.model.movie.Movie
import com.serefa.themovieapp.data.model.movie.local.Category
import com.serefa.themovieapp.databinding.ItemMovieBinding
import com.serefa.themovieapp.extensions.load

class MovieAdapter(val onMovieClick: (Movie) -> Unit): ListAdapter<Movie, RecyclerView.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MovieViewHolder).bind(getItem(position))
    }

    object DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
    }

    inner class MovieViewHolder private constructor(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        constructor(parent: ViewGroup) : this(ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))

        fun bind(item: Movie) {
            binding.apply {
                imageViewMovieImage.load(item.getThumbUrl(ImageQuality.THUMBNAIL))
                root.setOnClickListener {
                    onMovieClick(getItem(adapterPosition))
                }
            }
        }
    }
}