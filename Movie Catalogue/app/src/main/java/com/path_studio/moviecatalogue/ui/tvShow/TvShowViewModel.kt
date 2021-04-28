package com.path_studio.moviecatalogue.ui.tvShow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.path_studio.moviecatalogue.data.MovieEntity
import com.path_studio.moviecatalogue.data.TvShowEntity
import com.path_studio.moviecatalogue.data.source.TmdbRepository
import com.path_studio.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse
import com.path_studio.moviecatalogue.data.source.remote.response.ResultsItemTvShow
import com.path_studio.moviecatalogue.ui.movie.MovieViewModel
import com.path_studio.moviecatalogue.data.source.remote.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvShowViewModel(private val tmdbRepository: TmdbRepository): ViewModel() {
    fun getDiscoverTvShow(): LiveData<List<TvShowEntity>> = tmdbRepository.getDiscoverTvShow()
}