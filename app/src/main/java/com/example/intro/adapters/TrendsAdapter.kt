package com.example.intro.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.intro.R
import com.example.presentation.binding.MovieBinding
import com.rishabhharit.roundedimageview.RoundedImageView
import kotlinx.android.synthetic.main.trends_item_container.view.*

class TrendsAdapter : RecyclerView.Adapter<TrendsAdapter.TrendViewHolder>() {

    private val movies = ArrayList<MovieBinding>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendViewHolder {
        return TrendViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.trends_item_container, parent, false)
        )
    }

    override fun getItemCount(): Int = movies?.size

    override fun onBindViewHolder(holder: TrendViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun setData(newMovies: ArrayList<MovieBinding>) {
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }

    inner class TrendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewTrendMovie = itemView.mImageViewTrendMoviePoster as RoundedImageView
        private val textViewTrendMovieTitle = itemView.mTextViewTrendMovieTitle as TextView
        private val textViewTrendMovieOverview = itemView.mTextViewTrendMovieOverview as TextView

        fun bind(movie: MovieBinding) {
            textViewTrendMovieTitle.text = movie.title
            textViewTrendMovieOverview.text = movie.overview
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .centerCrop()
                .into(imageViewTrendMovie)
        }
    }
}