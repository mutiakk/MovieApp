package com.example.movieappbisa.entity.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.example.movieappbisa.entity.local.room.DataDao
import com.example.movieappbisa.entity.local.source.MovieData
import com.example.movieappbisa.entity.local.source.TvShowData

class LocalDataSource private constructor(private val dataDao: DataDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(dataDao: DataDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(dataDao)
    }

    //movie
    fun getAllMovies(): DataSource.Factory<Int, MovieData> = dataDao.getMovie()
    fun getDetailMovies(movieId: Int) : LiveData<MovieData> = dataDao.getIdMovie(movieId)
    fun getFavMovies(): DataSource.Factory<Int, MovieData> = dataDao.getFavMovie()
    fun insertMovies(movies: List<MovieData>) = dataDao.insertMovie(movies)
    fun insertMovieDetail(movie: MovieData) = dataDao.insertMovieDetail(movie)
    fun setFavMovie(movie : MovieData, state: Boolean){
        movie.isFavorite = !state
        dataDao.updateMovie(movie)
    }

    //tv
    fun getAllTv(): DataSource.Factory<Int, TvShowData> = dataDao.getTV()
    fun getFavTv(): DataSource.Factory<Int, TvShowData> = dataDao.getFavTV()
    fun getDetailTv(tvId: Int) : LiveData<TvShowData> = dataDao.getIdTv(tvId)
    fun insertTv(tv: List<TvShowData>) = dataDao.insertTV(tv)
    fun insertTvDetail(tv: TvShowData) = dataDao.insertTvDetail(tv)
    fun setFavTv(tv : TvShowData, state: Boolean) {
        tv.isFavorite = !state
        dataDao.updateTV(tv)
    }
}