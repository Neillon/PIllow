package com.example.intro.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.intro.R
import com.example.intro.databinding.TrendsItemContainerBinding
import com.example.intro.ui.actions.FavoriteMovieClick
import com.example.presentation.binding.MovieBinding

class TrendsAdapter(private val favoriteMovieClick: FavoriteMovieClick) :
    RecyclerView.Adapter<TrendsAdapter.TrendViewHolder>() {

    private val movies = ArrayList<MovieBinding>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<TrendsItemContainerBinding>(inflater, R.layout.trends_item_container, parent, false)
        return TrendViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: TrendViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun setData(newMovies: ArrayList<MovieBinding>) {
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }

    fun favoriteMovie(movie: MovieBinding) {
        val savedMovie = movies.firstOrNull { it.id == movie.id }
        savedMovie?.let {
            it.favorite = true;
        }
        notifyDataSetChanged()
    }

    inner class TrendViewHolder(var view: TrendsItemContainerBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(movie: MovieBinding) {
            view.movie = movie
            view.favoriteClick = favoriteMovieClick

//            iconButtonFavoriteMovie.setIconResource(if (movie.favorite) R.drawable.ic_star_solid else R.drawable.ic_star_border)
//            iconButtonFavoriteMovie.setOnClickListener { favoriteMovieClick.favoriteMovieClick(movie) }
        }
    }
}