package com.example.movieappbisa.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.movieappbisa.entity.MovieTvRepository
import com.example.movieappbisa.entity.local.source.TvShowData
import com.example.movieappbisa.ui.tvshow.TvshowViewModel
import com.example.movieappbisa.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvshowViewModelTest {
    private lateinit var tvShowViewModel: TvshowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<TvShowData>>>

    @Mock
    private lateinit var movieTvRepository : MovieTvRepository

    @Mock
    private lateinit var pagedList: PagedList<TvShowData>

    @Before
    fun setUp() {
        tvShowViewModel = TvshowViewModel(movieTvRepository)
    }

    @Test
    fun getTvShow() {
        val movie= Resource.success(pagedList)
        Mockito.`when`(movie.data?.size).thenReturn(1)
        val dummyMovie = MutableLiveData<Resource<PagedList<TvShowData>>>()
        dummyMovie.value= movie

        Mockito.`when`(movieTvRepository.getAllTv()).thenReturn(dummyMovie)
        val movieData = tvShowViewModel.getTvShows().value?.data

        Mockito.verify(movieTvRepository).getAllTv()
        assertNotNull(movieData)
        assertEquals(1, movieData?.size)

        tvShowViewModel.getTvShows().observeForever(observer)
        Mockito.verify(observer).onChanged(movie)
    }
}