package com.example.movieappbisa.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.movieappbisa.entity.MovieTvRepository
import com.example.movieappbisa.entity.local.source.MovieData
import com.example.movieappbisa.entity.local.source.TvShowData
import com.example.movieappbisa.ui.detail.DetailViewModel
import com.example.movieappbisa.util.DataDummy
import com.example.movieappbisa.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {
    private lateinit var detailViewModel: DetailViewModel

    private val dataDummyMov= DataDummy.getMovies()[0]
    private val dataDummyTv= DataDummy.getTvShow()[0]

    private val id= dataDummyMov.id
    private val id_tv= dataDummyTv.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    /**private var viewModel: HomeViewModel? = null
    private var data = Mockito.mock(MovieTvRepository::class.java)**/

    @Mock
    private lateinit var movieTvRepository: MovieTvRepository

    @Mock
    private lateinit var movieObserver: Observer<Resource<MovieData>>

    @Mock
    private lateinit var tvObserver: Observer<Resource<TvShowData>>

    //Test Movie
    @Before
    fun setUp() {
        detailViewModel= DetailViewModel(movieTvRepository)
    }


    @Test
    fun getDetail() {
        val dataDummyMov = Resource.success(DataDummy.getMovies()[0])
        val movie = MutableLiveData<Resource<MovieData>>()
        movie.value = dataDummyMov

        `when`(movieTvRepository.getMoviebyId(id)).thenReturn(movie)
        val detail= detailViewModel.getMovieDetail(id).value?.data

        verify(movieTvRepository).getMoviebyId(id)
        assertNotNull(detail)
        assertEquals(dataDummyMov.data?.id,detail?.id)
        assertEquals(dataDummyMov.data?.title,detail?.title)
        assertEquals(dataDummyMov.data?.release,detail?.release)
        assertEquals(dataDummyMov.data?.rate,detail?.rate)
        assertEquals(dataDummyMov.data?.poster,detail?.poster)
        assertEquals(dataDummyMov.data?.descr,detail?.descr)

        detailViewModel.getMovieDetail(id).observeForever(movieObserver)
        verify(movieObserver).onChanged(dataDummyMov)

    }

    //Test TV Show
    @Test
    fun getDetailTv() {
        val dataDummyTv = Resource.success(DataDummy.getTvShow()[0])
        val movie = MutableLiveData<Resource<TvShowData>>()
        movie.value = dataDummyTv

        `when`(movieTvRepository.getTvById(id_tv)).thenReturn(movie)
        val detail= detailViewModel.getTvDetail(id_tv).value?.data

        verify(movieTvRepository).getTvById(id_tv)
        assertNotNull(detail)
        assertEquals(dataDummyTv.data?.id,detail?.id)
        assertEquals(dataDummyTv.data?.title,detail?.title)
        assertEquals(dataDummyTv.data?.release,detail?.release)
        assertEquals(dataDummyTv.data?.rate,detail?.rate)
        assertEquals(dataDummyTv.data?.poster,detail?.poster)
        assertEquals(dataDummyTv.data?.descr,detail?.descr)

        detailViewModel.getTvDetail(id_tv).observeForever(tvObserver)
        verify(tvObserver).onChanged(dataDummyTv)

    }
}

