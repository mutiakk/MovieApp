package com.example.movieappbisa.entity.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowResponse (
    @field:SerializedName("results")
    val results: List<TvShow>
)
data class TvShow(
    @field:SerializedName("id")
    var id: Int,
    @field:SerializedName("name")
    var title: String,
    @field:SerializedName("first_air_date")
    var release: String,
    @field:SerializedName("overview")
    var descr: String,
    @field:SerializedName("vote_average")
    var rate: Double,
    @field:SerializedName("poster_path")
    var poster: String
)
