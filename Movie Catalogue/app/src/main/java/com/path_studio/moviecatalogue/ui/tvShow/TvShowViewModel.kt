package com.path_studio.moviecatalogue.ui.tvShow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.path_studio.moviecatalogue.data.source.remote.response.DiscoverTvShowResponse
import com.path_studio.moviecatalogue.data.source.remote.response.ResultsItemTvShow
import com.path_studio.moviecatalogue.ui.movie.MovieViewModel
import com.path_studio.moviecatalogue.util.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvShowViewModel: ViewModel() {

    private val _tvShow = MutableLiveData<DiscoverTvShowResponse>()
    val tvShow: LiveData<DiscoverTvShowResponse> = _tvShow

    private val _listTvShow = MutableLiveData<List<ResultsItemTvShow>>()
    val listTvShow: LiveData<List<ResultsItemTvShow>> = _listTvShow

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "TvShowViewModel"
    }

    init {
        discoverTvShow()
    }

    fun discoverTvShow() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDiscoverTvShow(MovieViewModel.API_KEY, "en-US")
        client.enqueue(object : Callback<DiscoverTvShowResponse> {
            override fun onResponse(
                    call: Call<DiscoverTvShowResponse>,
                    response: Response<DiscoverTvShowResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _tvShow.value = response.body()
                    _listTvShow.value = response.body()?.results
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DiscoverTvShowResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}