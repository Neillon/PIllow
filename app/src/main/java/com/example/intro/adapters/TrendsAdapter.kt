package com.example.intro.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.intro.R
import com.example.intro.model.TrendMovie
import com.rishabhharit.roundedimageview.RoundedImageView
import kotlinx.android.synthetic.main.trends_item_container.view.*

class TrendsAdapter : RecyclerView.Adapter<TrendsAdapter.TrendViewHolder>() {

    private lateinit var movies: List<TrendMovie>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendViewHolder {
        return TrendViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.trends_item_container, parent, false)
        )
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: TrendViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun setData(newMovies: List<TrendMovie>) {
        movies = newMovies
    }

    inner class TrendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewTrendMovie = itemView.mImageViewTrendMoviePoster as RoundedImageView
        private val textViewTrendMovieTitle = itemView.mTextViewTrendMovieTitle as TextView
        private val textViewTrendMovieOverview = itemView.mTextViewTrendMovieOverview as TextView

        fun bind(trendMovie: TrendMovie) {
            textViewTrendMovieTitle.text    = "Mad Max: Fury Road"
            textViewTrendMovieOverview.text = "An apocalyptic story set in the furthest reaches of our planet, in a stark desert landscape where humanity is broken, and most everyone is crazed fighting for the necessities of life. Within this world exist two rebels on the run who just might be able to restore order."
            Glide.with(itemView.context)
                .load(trendMovie.image)
                .centerCrop()
                .into(imageViewTrendMovie)
        }
    }
}