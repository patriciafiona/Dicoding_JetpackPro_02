package com.path_studio.moviecatalogue.ui.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.path_studio.moviecatalogue.R
import com.path_studio.moviecatalogue.data.source.remote.response.ResultsItemMovie
import com.path_studio.moviecatalogue.databinding.ItemsMovieTvshowBinding
import com.path_studio.moviecatalogue.ui.detailMovie.DetailMovieActivity
import com.path_studio.moviecatalogue.util.Utils

class MovieAdapter: RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var listMovies = ArrayList<ResultsItemMovie>()

    fun setMovies(movies: List<ResultsItemMovie>) {
        if (movies == null) return
        this.listMovies.clear()
        this.listMovies.addAll(movies)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemsMovieBinding = ItemsMovieTvshowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemsMovieBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = listMovies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = listMovies.size


    class MovieViewHolder(private val binding: ItemsMovieTvshowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: ResultsItemMovie) {
            with(binding) {
                tvItemTitle.text = movie.originalTitle
                tvItemDate.text = movie.releaseDate

                tvItemRating.rating = movie.voteAverage.toFloat()/2

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailMovieActivity::class.java)
                    intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie.id)
                    itemView.context.startActivity(intent)
                }

                val posterURL = "https://image.tmdb.org/t/p/w500/${movie.posterPath}"
                Glide.with(itemView.context)
                    .load(posterURL)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error))
                    .into(imgPoster)
            }
        }
    }

}