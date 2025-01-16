package com.serefa.themovieapp.data.model.movie.detail


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Genre(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "name")
    val name: String?
){
    companion object {
        val EMPTY_INSTANCE = Genre(0, "")
    }
}