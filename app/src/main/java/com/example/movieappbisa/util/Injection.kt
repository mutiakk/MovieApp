package com.example.movieappbisa.util

import android.content.Context
import com.example.movieappbisa.entity.MovieTvRepository
import com.example.movieappbisa.entity.local.LocalDataSource
import com.example.movieappbisa.entity.local.room.DataDb
import com.example.movieappbisa.entity.remote.RemoteDataSource
import com.example.movieappbisa.util.api.ApiConfig.Companion.getApi

object Injection {

    fun repoProvider(context:Context): MovieTvRepository {

        val database = DataDb.getInstance(context)
        val remoteDataSource = RemoteDataSource.getInstance(getApi())
        val localDataSource = LocalDataSource.getInstance(database.dataDao())
        val appExecutors = AppExecutors()

        return MovieTvRepository.getInstance(remoteDataSource,localDataSource, appExecutors)
    }

}