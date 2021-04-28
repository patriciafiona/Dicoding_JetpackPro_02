package com.path_studio.moviecatalogue.ui.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.path_studio.moviecatalogue.BuildConfig
import com.path_studio.moviecatalogue.data.MovieEntity
import com.path_studio.moviecatalogue.data.source.TmdbRepository
import com.path_studio.moviecatalogue.data.source.remote.response.DiscoverMovieResponse
import com.path_studio.moviecatalogue.data.source.remote.response.ResultsItemMovie
import com.path_studio.moviecatalogue.data.source.remote.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel(private val tmdbRepository: TmdbRepository): ViewModel() {
    fun getDiscoverMovies(): LiveData<List<MovieEntity>> = tmdbRepository.getDiscoverMovies()
}