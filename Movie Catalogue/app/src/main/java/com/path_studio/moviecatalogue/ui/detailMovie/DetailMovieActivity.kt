package com.path_studio.moviecatalogue.ui.detailMovie

import android.annotation.SuppressLint
import android.app.ActionBar
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.faltenreich.skeletonlayout.Skeleton
import com.path_studio.moviecatalogue.R
import com.path_studio.moviecatalogue.data.MovieEntity
import com.path_studio.moviecatalogue.data.source.remote.response.DetailMovieResponse
import com.path_studio.moviecatalogue.databinding.ActivityDetailMovieBinding
import com.path_studio.moviecatalogue.util.Utils.changeMinuteToDurationFormat
import com.path_studio.moviecatalogue.util.Utils.changeStringToDateFormat
import com.path_studio.moviecatalogue.util.Utils.showAlert


class DetailMovieActivity : AppCompatActivity(){
    private val detailMovieViewModel: DetailMovieViewModel by viewModels()

    private lateinit var binding: ActivityDetailMovieBinding
    private lateinit var skeleton: Skeleton

    private lateinit var movieDetails: MovieEntity

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init Skeleton
        skeleton = binding.skeletonLayout

        //show loading indicator
        showLoading(true)
        showYoutubeLoading(true)

        val extras = intent.extras
        if (extras != null) {
            val movieId = extras.getLong(EXTRA_MOVIE)
            if (movieId != 0L) {
                detailMovieViewModel.setSelectedMovie(movieId)
                val showDetails = detailMovieViewModel.detailMovie



                showDetails.observe(this, { detail ->
                    showDetailMovie(detail)
                })
            }
        }

        binding.btnBackPage.setOnClickListener {
            super.onBackPressed() // or super.finish();
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //outState.putParcelable(EXTRA_STATE, movieDetails)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun showDetailMovie(movieEntity: DetailMovieResponse) {
        if (!movieEntity.originalTitle.equals("") && movieEntity.originalTitle != null){
            showLoading(false)

            binding.movieTopTitle.text = movieEntity.title
            binding.movieTitle.text = movieEntity.title
            binding.movieSinopsis.text = movieEntity.overview

            binding.movieReleaseDate.text = changeStringToDateFormat(movieEntity.releaseDate.toString())

            binding.movieRating.rating = movieEntity.voteAverage!!.toFloat()/2

            binding.movieDuration.text = movieEntity.runtime?.let { changeMinuteToDurationFormat(it) }

            val posterURL = "https://image.tmdb.org/t/p/w500/${movieEntity.posterPath}"
            Glide.with(this)
                .load(posterURL)
                .transform(RoundedCorners(20))
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(binding.moviePoster)

            val backdropURL = "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/${movieEntity.posterPath}"
            Glide.with(this)
                .load(backdropURL)
                .transform(RoundedCorners(20))
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(binding.movieBackdrop)
            binding.movieBackdrop.alpha = 0.5F

            for (genre in movieEntity.genres!!){
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
                binding.movieGenres.addView(btnTag)
            }

            //set video in webview
            //setVideoWebView(movieEntity.youtubeVideoURL)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setVideoWebView(youtubeURL: String){
        binding.movieTrailer.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                showYoutubeLoading(false)
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                showAlert(this@DetailMovieActivity, "Failed to get Trailer Video")
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return false
            }
        }
        val webSettings: WebSettings = binding.movieTrailer.settings
        webSettings.javaScriptEnabled = true
        binding.movieTrailer.loadUrl(youtubeURL)
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