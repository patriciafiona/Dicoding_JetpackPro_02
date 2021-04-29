package com.path_studio.moviecatalogue.ui.tvShow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.path_studio.moviecatalogue.data.MovieEntity
import com.path_studio.moviecatalogue.data.TvShowEntity
import com.path_studio.moviecatalogue.data.source.TmdbRepository
import com.path_studio.moviecatalogue.ui.movie.MovieViewModel
import com.path_studio.moviecatalogue.util.DataDummy
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowViewModelTest {

    private lateinit var viewModel: TvShowViewModel

    @Mock
    private lateinit var tmdbRepository: TmdbRepository

    @Mock
    private lateinit var observer: Observer<List<TvShowEntity>>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = TvShowViewModel(tmdbRepository)
    }

    @Test
    fun getTvShows() {
        val shows = MutableLiveData<List<TvShowEntity>>()
        shows.value = DataDummy.generateDummyTvShow()

        Mockito.`when`(tmdbRepository.getDiscoverTvShow()).thenReturn(shows)

        val dataListShow = viewModel.getDiscoverTvShow().value
        Mockito.verify(tmdbRepository).getDiscoverTvShow()
        assertNotNull(dataListShow)
        assertEquals(10, dataListShow?.size)
    }
}