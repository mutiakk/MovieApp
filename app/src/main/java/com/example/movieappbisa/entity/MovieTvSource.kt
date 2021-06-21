package com.example.movieappbisa.entity

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.movieappbisa.entity.local.source.MovieData
import com.example.movieappbisa.entity.local.source.TvShowData
import com.example.movieappbisa.vo.Resource

interface MovieTvSource {

    //movie
    fun getAllMovie(): LiveData<Resource<PagedList<MovieData>>>

    fun getMoviebyId(id : Int) : LiveData<Resource<MovieData>>

    fun getFavMovie(): LiveData<PagedList<MovieData>>

    fun setFavMovie(movie: MovieData, state: Boolean)

    //TV
    fun getAllTv(): LiveData<Resource<PagedList<TvShowData>>>

    fun getTvById(id : Int) : LiveData<Resource<TvShowData>>

    fun getFavTv(): LiveData<PagedList<TvShowData>>

    fun setFavTv(tv: TvShowData, state: Boolean)

}