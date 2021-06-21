package com.example.movieappbisa.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import com.example.movieappbisa.entity.MovieTvRepository
import com.example.movieappbisa.entity.local.source.MovieData
import com.example.movieappbisa.entity.local.source.TvShowData
import com.example.movieappbisa.util.DataDummy
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executors

@RunWith(MockitoJUnitRunner::class)
class FavMovieTvModelTest {
    private lateinit var viewModel: FavMovieTvModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: MovieTvRepository

    @Mock
    private lateinit var movieObserver: Observer<PagedList<MovieData>>

    @Mock
    private lateinit var tvObserver: Observer<PagedList<TvShowData>>

    @Before
    fun setUp() {
        viewModel = FavMovieTvModel(movieTvRepository)
    }


    @Test
    fun `getFavoMovie should be success`() {

        val expected = MutableLiveData<PagedList<MovieData>>()
        expected.value = PagedTestDataSources.snapshot(DataDummy.getMovies())

        `when`(movieTvRepository.getFavMovie()).thenReturn(expected)

        viewModel.getFavoMovie().observeForever(movieObserver)
        Mockito.verify(movieObserver).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getFavoMovie().value
        assertEquals(expectedValue, actualValue)
        assertEquals(expectedValue?.snapshot(), actualValue?.snapshot())
        assertEquals(expectedValue?.size, actualValue?.size)

    }

    @Test
    fun `getFavoMovie should be access but data is empty`() {

        val expected = MutableLiveData<PagedList<MovieData>>()
        expected.value = PagedTestDataSources.snapshot()

        `when`(movieTvRepository.getFavMovie()).thenReturn(expected)

        viewModel.getFavoMovie().observeForever(movieObserver)
        Mockito.verify(movieObserver).onChanged(expected.value)

        val actualValueDataSize = viewModel.getFavoMovie().value?.size
        assertTrue("$actualValueDataSize", actualValueDataSize == 0)

    }

    @Test
    fun `getFavoTv should be success`() {

        val expected = MutableLiveData<PagedList<TvShowData>>()
        expected.value = PagedTestDataSourcesTV.snapshot2(DataDummy.getTvShow())

        `when`(movieTvRepository.getFavTv()).thenReturn(expected)

        viewModel.getFavoTv().observeForever(tvObserver)
        Mockito.verify(tvObserver).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getFavoTv().value
        assertEquals(expectedValue, actualValue)
        assertEquals(expectedValue?.snapshot(), actualValue?.snapshot())
        assertEquals(expectedValue?.size, actualValue?.size)

    }

    @Test
    fun `getFavoTv should be access but data is empty`() {

        val expected = MutableLiveData<PagedList<TvShowData>>()
        expected.value = PagedTestDataSourcesTV.snapshot2()

        `when`(movieTvRepository.getFavTv()).thenReturn(expected)

        viewModel.getFavoTv().observeForever(tvObserver)
        Mockito.verify(tvObserver).onChanged(expected.value)

        val actualValueDataSize = viewModel.getFavoTv().value?.size
        assertTrue("$actualValueDataSize", actualValueDataSize == 0)

    }

    class PagedTestDataSources private constructor(private val items: List<MovieData>) : PositionalDataSource<MovieData>() {
        companion object {
            fun snapshot(items: List<MovieData> = listOf()): PagedList<MovieData> {
                return PagedList.Builder(PagedTestDataSources(items), 10)
                    .setNotifyExecutor(Executors.newSingleThreadExecutor())
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .build()
            }
        }

        override fun loadInitial(
            params: LoadInitialParams,
            callback: LoadInitialCallback<MovieData>
        ) {
            callback.onResult(items, 0, items.size)
        }

        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<MovieData>) {
            val start = params.startPosition
            val end = params.startPosition + params.loadSize
            callback.onResult(items.subList(start, end))
        }
    }

    class PagedTestDataSourcesTV private constructor(private val items: List<TvShowData>) : PositionalDataSource<TvShowData>() {
        companion object {
            fun snapshot2(items: List<TvShowData> = listOf()): PagedList<TvShowData> {
                return PagedList.Builder(PagedTestDataSourcesTV(items), 10)
                    .setNotifyExecutor(Executors.newSingleThreadExecutor())
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .build()
            }
        }

        override fun loadInitial(
            params: LoadInitialParams,
            callback: LoadInitialCallback<TvShowData>) {
            callback.onResult(items, 0, items.size)
        }

        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<TvShowData>) {
            val start = params.startPosition
            val end = params.startPosition + params.loadSize
            callback.onResult(items.subList(start, end))
        }
    }
}