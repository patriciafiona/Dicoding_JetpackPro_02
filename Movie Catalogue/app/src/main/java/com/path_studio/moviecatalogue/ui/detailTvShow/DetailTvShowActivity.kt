package com.path_studio.moviecatalogue.ui.detailTvShow

import android.annotation.SuppressLint
import android.app.ActionBar
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.faltenreich.skeletonlayout.Skeleton
import com.path_studio.moviecatalogue.R
import com.path_studio.moviecatalogue.data.source.remote.response.DetailTvShowResponse
import com.path_studio.moviecatalogue.databinding.ActivityDetailTvShowBinding
import com.path_studio.moviecatalogue.util.Utils

class DetailTvShowActivity : AppCompatActivity() {

    private val detailTvShowViewModel: DetailTvShowViewModel by viewModels()

    private lateinit var binding: ActivityDetailTvShowBinding
    private lateinit var skeleton: Skeleton

    companion object {
        const val EXTRA_TV_SHOW = "extra_tv_show"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTvShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init Skeleton
        skeleton = binding.skeletonLayout

        //show loading indicator
        showLoading(true)
        showYoutubeLoading(true)

        //prepare view model for show Tv Show Details

        val extras = intent.extras
        if (extras != null) {
            val showId = extras.getLong(EXTRA_TV_SHOW)
            Log.e("showId", showId.toString())
            if (showId != 0L) {
                detailTvShowViewModel.setSelectedShow(showId)
                val showDetails = detailTvShowViewModel.detailShow

                val listOfSeason = detailTvShowViewModel.listSeason
                val seasonAdapter = SeasonDetailAdapter()

                listOfSeason.observe(this, { season ->
                    seasonAdapter.setSeason(season)
                    seasonAdapter.notifyDataSetChanged()
                })

                with(binding.rvSeasonDetail) {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = seasonAdapter
                    showLoading(false)
                }

                showDetails.observe(this, { detail ->
                    showDetailShow(detail)
                })
            }
        }

        binding.btnBackPage02.setOnClickListener {
            super.onBackPressed() // or super.finish();
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun showDetailShow(tvShowEntity: DetailTvShowResponse) {
        if (!tvShowEntity.originalName.equals("") && tvShowEntity.originalName != null){
            showLoading(false)

            binding.showTopTitle.text = tvShowEntity.originalName
            binding.showTitle.text = tvShowEntity.originalName
            binding.showSinopsis.text = tvShowEntity.overview

            binding.showReleaseDate.text = tvShowEntity.firstAirDate

            binding.showRating.rating = tvShowEntity.voteAverage!!.toFloat()/2

            binding.showDuration.text = tvShowEntity.episodeRunTime?.get(0)?.let {
                Utils.changeMinuteToDurationFormat(
                    it
                )
            }

            val posterURL = "https://image.tmdb.org/t/p/w500/${tvShowEntity.posterPath}"
            Glide.with(this)
                .load(posterURL)
                .transform(RoundedCorners(20))
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(binding.showPoster)


            val backdropURL = "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/${tvShowEntity.posterPath}"
            Glide.with(this)
                .load(backdropURL)
                .transform(RoundedCorners(20))
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(binding.showBackdrop)
            binding.showBackdrop.alpha = 0.5F

            for (genre in tvShowEntity.genres!!){
                //set the properties for button
                val btnTag = Button(this)

                //set margin and create button
                val params: ActionBar.LayoutParams = ActionBar.LayoutParams(
                    ActionBar.LayoutParams.WRAP_CONTENT,
                    ActionBar.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(0, 0, 20, 0)

                btnTag.layoutParams = ActionBar.LayoutParams(params)
                btnTag.text = genre.toString()
                btnTag.background = this.getDrawable(R.drawable.rounded_button)

                //set padding
                btnTag.setPadding(15, 10, 15, 10)

                //add button to the layout
                binding.showGenres.addView(btnTag)
            }

            //set video in webview
            //setVideoWebView(tvShowEntity.trailerURL)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setVideoWebView(youtubeURL: String){
        binding.showTrailer.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                showYoutubeLoading(false)
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                Utils.showAlert(this@DetailTvShowActivity, "Failed to get Trailer Video")
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return false
            }
        }
        val webSettings: WebSettings = binding.showTrailer.settings
        webSettings.javaScriptEnabled = true
        binding.showTrailer.loadUrl(youtubeURL)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            skeleton.showSkeleton()
        } else {
            skeleton.showOriginal()
        }
    }

    private fun showYoutubeLoading(state: Boolean) {
        if (state) {
            binding.youtubeProgressBar.visibility = View.VISIBLE
        } else {
            binding.youtubeProgressBar.visibility = View.GONE
        }
    }

}