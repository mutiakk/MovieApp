package com.example.movieappbisa.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.movieappbisa.entity.MovieTvRepository
import com.example.movieappbisa.entity.local.source.MovieData
import com.example.movieappbisa.entity.local.source.TvShowData

class FavMovieTvModel(private val movieTvRepo:MovieTvRepository) : ViewModel() {

    fun getFavoMovie(): LiveData<PagedList<MovieData>> = movieTvRepo.getFavMovie()

    fun getFavoTv(): LiveData<PagedList<TvShowData>> = movieTvRepo.getFavTv()

    fun setFavoriteMovie(movieEntity: MovieData, newState: Boolean) {
        movieTvRepo.setFavMovie(movieEntity, newState)
    }

    fun setFavoriteTv(tvEntity: TvShowData, newState: Boolean) {
        movieTvRepo.setFavTv(tvEntity, newState)
    }
}