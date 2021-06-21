package com.example.movieappbisa.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.movieappbisa.entity.MovieTvRepository
import com.example.movieappbisa.entity.local.source.TvShowData
import com.example.movieappbisa.vo.Resource


class TvshowViewModel(private val movieTvRepo: MovieTvRepository) : ViewModel() {
    fun getTvShows(): LiveData<Resource<PagedList<TvShowData>>> = movieTvRepo.getAllTv()
}
