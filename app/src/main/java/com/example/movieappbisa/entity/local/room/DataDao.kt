package com.example.movieappbisa.entity.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.example.movieappbisa.entity.local.source.MovieData
import com.example.movieappbisa.entity.local.source.TvShowData

@Dao
interface DataDao {
    @Query("SELECT * FROM movieDB")
    fun getMovie(): DataSource.Factory<Int, MovieData>

    @Query("SELECT * FROM movieDB WHERE isFavorite = 1")
    fun getFavMovie(): DataSource.Factory<Int, MovieData>

    @Query("SELECT * FROM movieDB WHERE movieId = :movieId")
    fun getIdMovie(movieId: Int) : LiveData<MovieData>

    //TV
    @Query("SELECT * FROM tvShowDB")
    fun getTV(): DataSource.Factory<Int, TvShowData>

    @Query("SELECT * FROM tvShowDB WHERE isFavorite = 1")
    fun getFavTV(): DataSource.Factory<Int, TvShowData>

    @Query("SELECT * FROM tvShowDB WHERE tvId = :tvId")
    fun getIdTv(tvId: Int) : LiveData<TvShowData>

    //Insert
    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = MovieData::class)
    fun insertMovie(movies: List<MovieData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = MovieData::class)
    fun insertMovieDetail(movie: MovieData)

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = TvShowData::class)
    fun insertTV(tv: List<TvShowData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = TvShowData::class)
    fun insertTvDetail(movie: TvShowData)

    //Update
    @Update(entity = MovieData::class)
    fun updateMovie(movie: MovieData)

    @Update(entity = TvShowData::class)
    fun updateTV(tv: TvShowData)
}