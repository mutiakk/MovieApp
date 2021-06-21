package com.example.movieappbisa.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movieappbisa.entity.MovieTvRepository
import com.example.movieappbisa.entity.local.source.MovieData
import com.example.movieappbisa.entity.local.source.TvShowData
import com.example.movieappbisa.vo.Resource

class DetailViewModel (private val movieTvRepo: MovieTvRepository) : ViewModel() {


    fun getMovieDetail(id : Int) : LiveData<Resource<MovieData>> = movieTvRepo.getMoviebyId(id)

    fun getTvDetail(id: Int) : LiveData<Resource<TvShowData>> = movieTvRepo.getTvById(id)

    fun setFavMovie(movie: MovieData, state: Boolean) = movieTvRepo.setFavMovie(movie, state)

    fun setFavTV(tv: TvShowData, state: Boolean) = movieTvRepo.setFavTv(tv, state)

}
    /**private val movieId = MutableLiveData<Int>()
    private val tvShowId = MutableLiveData<Int>()

    fun clickMovieId(movieId: Int) {
        this.movieId.value = movieId
    }

    fun clickTvShowId(tvShowId: Int) {
        this.tvShowId.value = tvShowId
    }

    var movieDetail: LiveData<Resource<MovieData>> =
        Transformations.switchMap(movieId) { mMovieId ->
            movieTvRepo.getMoviebyId(mMovieId)
        }

    var tvShowDetail: LiveData<Resource<TvShowData>> =
        Transformations.switchMap(tvShowId) { mTvShowId ->
            movieTvRepo.getTvById(mTvShowId)
        }

    fun setFavoriteMovie() {
        val movieResource = movieDetail.value?.data
        if (movieResource != null) {
            val newState = !movieResource.isFavorite
            movieTvRepo.setFavMovie(movieResource, newState)
        }
    }

    fun setFavoriteTvShow() {
        val tvShowResource = tvShowDetail.value?.data
        if (tvShowResource != null) {
            val newState = !tvShowResource.isFavorite
            movieTvRepo.setFavTv(tvShowResource, newState)
        }
    }
}**/