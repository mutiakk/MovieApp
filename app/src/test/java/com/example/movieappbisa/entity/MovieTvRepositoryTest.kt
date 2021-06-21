package com.example.movieappbisa.entity

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.movieappbisa.LiveDataTest
import com.example.movieappbisa.entity.local.LocalDataSource
import com.example.movieappbisa.entity.local.source.MovieData
import com.example.movieappbisa.entity.local.source.TvShowData
import com.example.movieappbisa.entity.remote.RemoteDataSource
import com.example.movieappbisa.util.AppExecutors
import com.example.movieappbisa.util.DataDummy
import com.example.movieappbisa.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase.assertEquals
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class MovieTvRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    private val movieTvFakeRepository = FakeMovieTvRepository(remote,local,appExecutors)

    private val movieResponses = DataDummy.getMoviesDummy()
    private val movieId = movieResponses[0].id
    private val tvShowResponses = DataDummy.getTvShowDummy()
    private val tvShowId = tvShowResponses[0].id

    @Test
    fun testGetAllMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieData>
        `when`(local.getAllMovies()).thenReturn(dataSourceFactory)
        movieTvFakeRepository.getAllMovie()


        val movieData = Resource.success(PagedUtil.mockPagedList(movieResponses))
        verify(local).getAllMovies()
        Assert.assertNotNull(movieData.data)
        assertEquals(movieResponses.size.toLong(), movieData.data?.size?.toLong())
    }

    @Test
    fun testGetMovieById() {
        val dummyMovie = MutableLiveData<MovieData>()
        dummyMovie.value = DataDummy.getMovies()[0]
        Mockito.`when`(movieId.let { local.getDetailMovies(it) }).thenReturn(dummyMovie)

        val result = LiveDataTest.getValue(movieTvFakeRepository.getMoviebyId(movieId))
        val movieResponse = movieResponses[0]

        verify(local).getDetailMovies(movieId)
        Assert.assertNotNull(result)
        assertEquals(movieResponse.id, result.data?.id)
        assertEquals(movieResponse.title, result.data?.title)
        assertEquals(movieResponse.release, result.data?.release)
        assertEquals(movieResponse.descr, result.data?.descr)
        assertEquals(movieResponse.rate, result.data?.rate)
        assertEquals(movieResponse.poster, result.data?.poster)
    }

    @Test
    fun testGetAllTvShows() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowData>
        Mockito.`when`(local.getAllTv()).thenReturn(dataSourceFactory)
        movieTvFakeRepository.getAllTv()

        val tvData = Resource.success(PagedUtil.mockPagedList(tvShowResponses))

        verify(local).getAllTv()
        Assert.assertNotNull(tvData.data)
        assertEquals(tvShowResponses.size.toLong(), tvData.data?.size?.toLong())
    }

    @Test
    fun testGetTvShowById() {
        val dummyTv= MutableLiveData<TvShowData>()
        dummyTv.value = DataDummy.getTvShow()[0]
        Mockito.`when`(tvShowId.let { local.getDetailTv(it) }).thenReturn(dummyTv)

        val result = LiveDataTest.getValue(movieTvFakeRepository.getTvById(tvShowId))
        val tvRespons = tvShowResponses[0]

        verify(local).getDetailTv(tvShowId)
        Assert.assertNotNull(result)
        assertEquals(tvRespons.id, result.data?.id)
        assertEquals(tvRespons.title, result.data?.title)
        assertEquals(tvRespons.release, result.data?.release)
        assertEquals(tvRespons.descr, result.data?.descr)
        assertEquals(tvRespons.rate, result.data?.rate)
        assertEquals(tvRespons.poster, result.data?.poster)
    }
}