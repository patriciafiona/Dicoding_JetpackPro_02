package com.path_studio.moviecatalogue.ui.detailMovie

import android.annotation.SuppressLint
import android.app.ActionBar
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.faltenreich.skeletonlayout.Skeleton
import com.path_studio.moviecatalogue.R
import com.path_studio.moviecatalogue.data.DetailMovieEntity
import com.path_studio.moviecatalogue.databinding.ActivityDetailMovieBinding
import com.path_studio.moviecatalogue.di.Injection
import com.path_studio.moviecatalogue.util.Utils.changeMinuteToDurationFormat
import com.path_studio.moviecatalogue.util.Utils.changeStringToDateFormat


class DetailMovieActivity : AppCompatActivity(){
    private lateinit var detailMovieViewModel: DetailMovieViewModel

    private lateinit var binding: ActivityDetailMovieBinding
    private lateinit var skeleton: Skeleton

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init Skeleton
        skeleton = binding.skeletonLayout

        val extras = intent.extras
        if (extras != null) {
            val movieId = extras.getLong(EXTRA_MOVIE)
            if (movieId != 0L) {
                detailMovieViewModel = DetailMovieViewModel(Injection.provideImdbRepository(this))
                detailMovieViewModel.getDetailMovie(movieId.toString())
                val showDetails = detailMovieViewModel.getDetailMovie(movieId.toString())

                showDetails.observe(this, { detail ->
                    showDetailMovie(detail)
                })

                detailMovieViewModel.getLoading().observe(this, {
                    if (it) skeleton.showSkeleton() else skeleton.showOriginal()
                })
            }
        }

        binding.btnBackPage.setOnClickListener {
            super.onBackPressed() // or super.finish();
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun showDetailMovie(movieEntity: DetailMovieEntity) {
        if (!movieEntity.title.equals("") && movieEntity.title != null){
            binding.movieTopTitle.text = movieEntity.title
            binding.movieTitle.text = movieEntity.title
            binding.movieSinopsis.text = movieEntity.overview

            binding.movieReleaseDate.text = movieEntity.releaseDate?.let { changeStringToDateFormat(it) }

            binding.movieRating.rating = movieEntity.voteAverage!!.toFloat()/2

            binding.movieDuration.text = changeMinuteToDurationFormat(movieEntity.runtime!!)

            val posterURL = "https://image.tmdb.org/t/p/w500${movieEntity.posterPath}"
            Glide.with(this)
                .load(posterURL)
                .transform(RoundedCorners(20))
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(binding.moviePoster)

            val backdropURL = "https://www.themoviedb.org/t/p/w533_and_h300_bestv2${movieEntity.backdropPath}"
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
                btnTag.text = genre
                btnTag.background = this.getDrawable(R.drawable.rounded_button)

                //set padding
                btnTag.setPadding(15, 10, 15, 10)

                //add button to the layout
                binding.movieGenres.addView(btnTag)
            }
        }
    }

}