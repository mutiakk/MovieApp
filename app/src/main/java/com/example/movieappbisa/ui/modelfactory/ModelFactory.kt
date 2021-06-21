package com.example.movieappbisa.ui.modelfactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieappbisa.util.Injection
import com.example.movieappbisa.ui.detail.DetailViewModel
import com.example.movieappbisa.entity.MovieTvRepository
import com.example.movieappbisa.ui.favorite.FavMovieTvModel
import com.example.movieappbisa.ui.home.HomeViewModel
import com.example.movieappbisa.ui.tvshow.TvshowViewModel

class ModelFactory private constructor(private val movieTvRepo: MovieTvRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ModelFactory? = null

        fun getInstance(context: Context): ModelFactory =
            instance ?: synchronized(this) {
                instance ?: ModelFactory(Injection.repoProvider(context))
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(movieTvRepo) as T
            }
            modelClass.isAssignableFrom(TvshowViewModel::class.java) -> {
                TvshowViewModel(movieTvRepo) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(movieTvRepo) as T
            }
            modelClass.isAssignableFrom(FavMovieTvModel::class.java) -> {
                FavMovieTvModel(movieTvRepo) as T
            }
            else -> throw Throwable("Unknown ViewModel class: ${modelClass.name}")
        }
    }

}