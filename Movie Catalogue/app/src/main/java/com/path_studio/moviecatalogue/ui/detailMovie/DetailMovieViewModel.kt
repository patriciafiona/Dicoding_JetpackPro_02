package com.path_studio.moviecatalogue.ui.detailMovie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.path_studio.moviecatalogue.BuildConfig
import com.path_studio.moviecatalogue.data.MovieEntity
import com.path_studio.moviecatalogue.data.source.remote.response.DetailMovieResponse
import com.path_studio.moviecatalogue.data.source.remote.response.DiscoverMovieResponse
import com.path_studio.moviecatalogue.ui.movie.MovieViewModel
import com.path_studio.moviecatalogue.util.ApiConfig
import com.path_studio.moviecatalogue.util.DataDummy
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMovieViewModel: ViewModel(){

    private val _detailMovie = MutableLiveData<DetailMovieResponse>()
    val detailMovie: LiveData<DetailMovieResponse> = _detailMovie

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var movieId: Long = 0L

    companion object{
        private const val TAG = "DetailMovieViewModel"
    }

    fun setSelectedMovie(movieId: Long) {
        this.movieId = movieId
        findMovie()
    }

    private fun findMovie() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailMovie(movieId.toString(), MovieViewModel.API_KEY, "en-US")
        client.enqueue(object : Callback<DetailMovieResponse> {
            override fun onResponse(
                call: Call<DetailMovieResponse>,
                response: Response<DetailMovieResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailMovie.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailMovieResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

}