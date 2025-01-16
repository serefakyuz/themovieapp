package com.serefa.themovieapp.data.model.movie.detail


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BelongsToCollection(
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "poster_path")
    val posterPath: String?
){
    companion object{
        val EMPTY_INSTANCE = BelongsToCollection("", 0, "", "")

    }
}