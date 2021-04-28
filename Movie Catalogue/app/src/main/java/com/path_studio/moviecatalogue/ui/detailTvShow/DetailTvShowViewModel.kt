package com.path_studio.moviecatalogue.ui.detailTvShow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.path_studio.moviecatalogue.data.source.remote.response.DetailTvShowResponse
import com.path_studio.moviecatalogue.data.source.remote.response.SeasonsItem
import com.path_studio.moviecatalogue.ui.movie.MovieViewModel
import com.path_studio.moviecatalogue.util.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailTvShowViewModel:ViewModel() {

    private val _detailShow = MutableLiveData<DetailTvShowResponse>()
    val detailShow: LiveData<DetailTvShowResponse> = _detailShow

    private val _listSeason = MutableLiveData<List<SeasonsItem>>()
    val listSeason: LiveData<List<SeasonsItem>> = _listSeason

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var showId: Long = 0L

    companion object{
        private const val TAG = "DetailTvShowViewModel"
    }

    fun setSelectedShow(showId: Long) {
        this.showId = showId
        findTvShow()
    }

    private fun findTvShow() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailTvShow(showId.toString(), MovieViewModel.API_KEY, "en-US")
        client.enqueue(object : Callback<DetailTvShowResponse> {
            override fun onResponse(
                call: Call<DetailTvShowResponse>,
                response: Response<DetailTvShowResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailShow.value = response.body()
                    _listSeason.value = response.body()?.seasons as List<SeasonsItem>?
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailTvShowResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

}