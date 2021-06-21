package com.example.movieappbisa.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.movieappbisa.entity.MovieTvRepository
import com.example.movieappbisa.entity.local.source.MovieData
import com.example.movieappbisa.ui.home.HomeViewModel
import com.example.movieappbisa.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    private lateinit var homeViewModel: HomeViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MovieData>>>

    @Mock
    private lateinit var pagedList: PagedList<MovieData>

    @Mock
    private lateinit var movieTvRepository : MovieTvRepository


    @Before
    fun setUp() {
        homeViewModel = HomeViewModel(movieTvRepository)
    }

    @Test
    fun getMovies() {
        val movie= Resource.success(pagedList)
        `when`(movie.data?.size).thenReturn(2)
        val dummyMovie = MutableLiveData<Resource<PagedList<MovieData>>>()
        dummyMovie.value= movie

        `when`(movieTvRepository.getAllMovie()).thenReturn(dummyMovie)
        val movieData = homeViewModel.getMovies().value?.data

        Mockito.verify(movieTvRepository).getAllMovie()
        assertNotNull(movieData)
        assertEquals(2, movieData?.size)

        homeViewModel.getMovies().observeForever(observer)
        Mockito.verify(observer).onChanged(movie)

    }
}




