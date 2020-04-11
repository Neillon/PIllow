package com.example.intro.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.intro.R
import com.example.intro.databinding.TrendsItemContainerBinding
import com.example.intro.ui.actions.FavoriteMovieClick
import com.example.presentation.binding.MovieBinding

class TrendItemCallback : DiffUtil.ItemCallback<MovieBinding>() {
    override fun areItemsTheSame(oldItem: MovieBinding, newItem: MovieBinding) =
        oldItem.favorite == newItem.favorite

    override fun areContentsTheSame(oldItem: MovieBinding, newItem: MovieBinding) =
        oldItem.id == newItem.id
}

class TrendsAdapter(private val favoriteMovieClick: FavoriteMovieClick) :
    ListAdapter<MovieBinding, TrendsAdapter.TrendViewHolder>(TrendItemCallback()) {

    private val movies = arrayListOf<MovieBinding>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<TrendsItemContainerBinding>(
            inflater,
            R.layout.trends_item_container,
            parent,
            false
        )
        return TrendViewHolder(view)
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

    fun favoriteMovie(movie: MovieBinding) {
        val savedMovie = movies.firstOrNull { it.id == movie.id }
        savedMovie?.let {
            it.favorite = true;
        }
        notifyDataSetChanged()
    }

    inner class TrendViewHolder(var view: TrendsItemContainerBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(movie: MovieBinding) {
            view.movie = movie
            view.favoriteClick = favoriteMovieClick
        }
    }
}