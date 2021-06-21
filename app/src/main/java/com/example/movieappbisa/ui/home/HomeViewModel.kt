package com.example.movieappbisa.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.movieappbisa.entity.MovieTvRepository
import com.example.movieappbisa.entity.local.source.MovieData
import com.example.movieappbisa.vo.Resource

class HomeViewModel (private val movieTvRepository: MovieTvRepository) : ViewModel() {
    fun getMovies() : LiveData<Resource<PagedList<MovieData>>> = movieTvRepository.getAllMovie()
}