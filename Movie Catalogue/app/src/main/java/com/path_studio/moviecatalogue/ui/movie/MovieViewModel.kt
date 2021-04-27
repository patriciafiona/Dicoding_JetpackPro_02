package com.path_studio.moviecatalogue.ui.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.path_studio.moviecatalogue.BuildConfig
import com.path_studio.moviecatalogue.data.MovieEntity
import com.path_studio.moviecatalogue.data.source.remote.response.DiscoverMovieResponse
import com.path_studio.moviecatalogue.data.source.remote.response.ResultsItemMovie
import com.path_studio.moviecatalogue.util.ApiConfig
import com.path_studio.moviecatalogue.util.DataDummy
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel: ViewModel() {

    private val _movies = MutableLiveData<DiscoverMovieResponse>()
    val movies: LiveData<DiscoverMovieResponse> = _movies

    private val _listMovie = MutableLiveData<List<ResultsItemMovie>>()
    val listMovie: LiveData<List<ResultsItemMovie>> = _listMovie

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        const val API_KEY = BuildConfig.TMDB_API_KEY
        private const val TAG = "MovieViewModel"
    }

    init {
        discoverMovie()
    }

    fun discoverMovie() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDiscoverMovie(API_KEY, "en-US")
        client.enqueue(object : Callback<DiscoverMovieResponse> {
            override fun onResponse(
                call: Call<DiscoverMovieResponse>,
                response: Response<DiscoverMovieResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _movies.value = response.body()
                    _listMovie.value = response.body()?.results
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DiscoverMovieResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}