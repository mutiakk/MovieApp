package com.example.movieappbisa.entity.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @field:SerializedName("results")
    val results: List<Movie>
)
data class Movie(
    @field:SerializedName("id")
    var id: Int,
    @field:SerializedName("title")
    var title: String,
    @field:SerializedName("release_date")
    var release: String,
    @field:SerializedName("overview")
    var descr: String,
    @field:SerializedName("vote_average")
    var rate: Double,
    @field:SerializedName("poster_path")
    var poster: String
)

