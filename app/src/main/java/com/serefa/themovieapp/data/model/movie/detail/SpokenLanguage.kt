package com.serefa.themovieapp.data.model.movie.detail


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SpokenLanguage(
    @Json(name = "english_name")
    val englishName: String?,
    @Json(name = "iso_639_1")
    val iso6391: String?,
    @Json(name = "name")
    val name: String?
){
    companion object {
        val EMPTY_INSTANCE = SpokenLanguage("", "", "")
    }
}