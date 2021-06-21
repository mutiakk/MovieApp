package com.example.movieappbisa.util.api

import com.example.movieappbisa.entity.remote.response.Movie
import com.example.movieappbisa.entity.remote.response.MovieResponse
import com.example.movieappbisa.entity.remote.response.TvShow
import com.example.movieappbisa.entity.remote.response.TvShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("discover/movie")
    fun getMovies(
        @Query("api_key")
        apiKey: String = "0b11553bd7c5fb6bc40585c26144f961"
    ): Call<MovieResponse>

    @GET("discover/tv?api_key=0b11553bd7c5fb6bc40585c26144f961")
    fun getTvShows(
        @Query("api_key")
        apiKey: String = "0b11553bd7c5fb6bc40585c26144f961"
    ): Call<TvShowResponse>

    @GET("movie/{id}")
    fun getMovieId(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = "0b11553bd7c5fb6bc40585c26144f961"
    ): Call<Movie>

    @GET("tv/{id}")
    fun getTvId(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = "0b11553bd7c5fb6bc40585c26144f961"
    ): Call<TvShow>



}