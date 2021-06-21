package com.example.movieappbisa.entity.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movieappbisa.entity.remote.response.ApiResponse
import com.example.movieappbisa.entity.remote.response.Movie
import com.example.movieappbisa.entity.remote.response.TvShow
import com.example.movieappbisa.util.Idling
import com.example.movieappbisa.util.api.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.await
import java.io.IOException

class RemoteDataSource private constructor(private val apiService: ApiService){

    companion object{
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this){
                instance ?: RemoteDataSource(service).apply { instance = this}
            }
    }

    fun getMovie(): LiveData<ApiResponse<List<Movie>>> {
        Idling.increment()
        val resultMovie = MutableLiveData<ApiResponse<List<Movie>>>()
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = apiService.getMovies().await()
                resultMovie.postValue(ApiResponse.success(response.results))
            } catch (e: IOException){
                e.printStackTrace()
                resultMovie.postValue(
                    ApiResponse.error(
                        e.message.toString(),
                        mutableListOf()
                    )
                )
            }
        }
        Idling.decrement()
        return resultMovie
    }

    fun getMovieDetail(Id: Int): LiveData<ApiResponse<Movie>> {
        Idling.increment()
        val resultDetailMovie = MutableLiveData<ApiResponse<Movie>>()
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiService.getMovieId(Id).await()
            resultDetailMovie.postValue(ApiResponse.success(response))
        }
        Idling.decrement()
        return resultDetailMovie
    }


    fun getTvShow(): LiveData<ApiResponse<List<TvShow>>> {
        Idling.increment()
        val resultTV = MutableLiveData<ApiResponse<List<TvShow>>>()
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiService.getTvShows().await()
            resultTV.postValue(ApiResponse.success(response.results))
        }
        Idling.decrement()
        return resultTV
    }

    fun getDetailTv(Id: Int): LiveData<ApiResponse<TvShow>>{
        Idling.increment()
        val resultDetailTV = MutableLiveData<ApiResponse<TvShow>>()
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiService.getTvId(Id).await()
            resultDetailTV.postValue(ApiResponse.success(response))
        }
        Idling.decrement()
        return  resultDetailTV
    }

}