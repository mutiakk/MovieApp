package com.example.movieappbisa.entity

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.movieappbisa.entity.local.LocalDataSource
import com.example.movieappbisa.entity.local.source.MovieData
import com.example.movieappbisa.entity.local.source.TvShowData
import com.example.movieappbisa.entity.remote.RemoteDataSource
import com.example.movieappbisa.entity.remote.response.ApiResponse
import com.example.movieappbisa.entity.remote.response.Movie
import com.example.movieappbisa.entity.remote.response.TvShow
import com.example.movieappbisa.util.AppExecutors
import com.example.movieappbisa.vo.Resource

class MovieTvRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : MovieTvSource {

    companion object {
        @Volatile
        private var instance: MovieTvRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ):
                MovieTvRepository =
            instance ?: synchronized(this) {
                instance ?: MovieTvRepository(remoteData, localData, appExecutors)
                    .apply { instance = this }
            }
    }

    override fun getAllMovie(): LiveData<Resource<PagedList<MovieData>>> {
        return object : NetworkBoundResource<PagedList<MovieData>, List<Movie>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<MovieData>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieData>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<Movie>>> =
                remoteDataSource.getMovie()

            public override fun saveCallResult(data: List<Movie>) {
                val movieList = ArrayList<MovieData>()
                for (movieResult in data) {
                    val movies = MovieData(
                        movieResult.id,
                        movieResult.title,
                        movieResult.release,
                        movieResult.descr,
                        movieResult.rate,
                        movieResult.poster,
                        false
                    )
                    movieList.add(movies)
                }
                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getMoviebyId(id: Int): LiveData<Resource<MovieData>> {
        return object : NetworkBoundResource<MovieData, Movie>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieData> =
                localDataSource.getDetailMovies(id)

            override fun shouldFetch(data: MovieData?): Boolean =
                data == null

            public override fun createCall(): LiveData<ApiResponse<Movie>> =
                remoteDataSource.getMovieDetail(id)

            override fun saveCallResult(response: Movie) {
                val movie = MovieData(
                    response.id,
                    response.title,
                    response.release,
                    response.descr,
                    response.rate,
                    response.poster,
                    false
                )
                localDataSource.insertMovieDetail(movie)
            }
        }.asLiveData()
    }

    override fun getFavMovie(): LiveData<PagedList<MovieData>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavMovies(), config).build()
    }

    override fun setFavMovie(movie: MovieData, state: Boolean) {
        return appExecutors.diskIO().execute {
            localDataSource.setFavMovie(movie, state)
        }
    }

    override fun getAllTv(): LiveData<Resource<PagedList<TvShowData>>> {
        return object : NetworkBoundResource<PagedList<TvShowData>, List<TvShow>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<TvShowData>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllTv(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowData>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<TvShow>>> =
                remoteDataSource.getTvShow()

            public override fun saveCallResult(data: List<TvShow>) {
                val tvList = ArrayList<TvShowData>()
                for (movieResult in data) {
                    val movies = TvShowData(
                        movieResult.id,
                        movieResult.title,
                        movieResult.release,
                        movieResult.descr,
                        movieResult.rate,
                        movieResult.poster,
                        false
                    )
                    tvList.add(movies)
                }
                localDataSource.insertTv(tvList)
            }
        }.asLiveData()
    }

    override fun getTvById(id: Int): LiveData<Resource<TvShowData>> {
        return object : NetworkBoundResource<TvShowData, TvShow>(appExecutors) {
            override fun loadFromDB(): LiveData<TvShowData> =
                localDataSource.getDetailTv(id)

            override fun shouldFetch(data: TvShowData?): Boolean =
                data == null

            public override fun createCall(): LiveData<ApiResponse<TvShow>> =
                remoteDataSource.getDetailTv(id)

            override fun saveCallResult(response: TvShow) {
                val movie = TvShowData(
                    response.id,
                    response.title,
                    response.release,
                    response.descr,
                    response.rate,
                    response.poster,
                    false
                )
                localDataSource.insertTvDetail(movie)
            }
        }.asLiveData()
    }

    override fun getFavTv(): LiveData<PagedList<TvShowData>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavTv(), config).build()
    }

    override fun setFavTv(tv: TvShowData, state: Boolean) {
        return appExecutors.diskIO().execute {
            localDataSource.setFavTv(tv, state)
        }
    }
}
/**
    override fun getMovieById(movieId: String): LiveData<MovieData> {
        val resultMovie = MutableLiveData<MovieData>()
        remoteDataSource.getMovies(object : RemoteDataSource.LoadMoviesCallback {
            override fun onAllMoviesReceived(movieResponses: List<Movie>?) {
                lateinit var movie: MovieData
                if (movieResponses != null) {
                    for (movieItem in movieResponses) {
                        if (movieId == movieItem.id.toString())
                            with(movieItem) {
                                movie =
                                    MovieData(id, title, release, descr, rate, poster)
                            }
                    }
                }
                resultMovie.postValue(movie)
            }

        })
        return resultMovie
    }

    override fun getAllTvShows(): LiveData<List<TvShowData>> {
        val resultTv: MutableLiveData<List<TvShowData>> = MutableLiveData<List<TvShowData>>()
        remoteDataSource.run {
            getTv(object : RemoteDataSource.LoadTvCallback {

                override fun onAllTvReceived(tvResponses: List<TvShow>?) {
                    val listTv: ArrayList<TvShowData> = ArrayList()
                    if (tvResponses != null) {
                        for (tv in tvResponses) {
                            with(tv) {
                                listTv.add(
                                    TvShowData(id, title, release, descr, rate, poster)
                                )
                            }
                        }
                    }
                    resultTv.postValue((listTv))
                }

            })
            return resultTv
        }

    }

    override fun getTvShowById(tvShowId: String): LiveData<TvShowData> {
        val resultTv = MutableLiveData<TvShowData>()
        remoteDataSource.getTv(object : RemoteDataSource.LoadTvCallback {

            override fun onAllTvReceived(tvResponses: List<TvShow>?) {
                lateinit var tv: TvShowData
                if (tvResponses != null) {
                    for (tvItem in tvResponses) {
                        if (tvShowId == tvItem.id.toString())
                            with(tvItem) {
                                tv =
                                    TvShowData(id, title, release, descr, rate, poster)
                            }
                    }
                }
                resultTv.postValue(tv)
            }
        })
        return resultTv
    }**/
