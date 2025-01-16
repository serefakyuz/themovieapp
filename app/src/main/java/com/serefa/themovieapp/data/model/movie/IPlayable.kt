package com.serefa.themovieapp.data.model.movie

import com.serefa.themovieapp.data.model.movie.local.ImageQuality

interface IPlayable {
    fun getThumbUrl(quality: ImageQuality): String?
    fun getVideoUrl(): String?
}