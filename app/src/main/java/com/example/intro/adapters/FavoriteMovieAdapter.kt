package com.example.intro.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.intro.R
import com.example.intro.databinding.FavoriteMovieItemContainerBinding
import com.example.intro.ui.actions.MovieItemClick
import com.example.presentation.binding.MovieBinding

class FavoriteMovieAdapter(private val movieItemClick: MovieItemClick) :
    RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieViewHolder>() {

    private var movies = arrayListOf<MovieBinding>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<FavoriteMovieItemContainerBinding>(
            inflater,
            R.layout.favorite_movie_item_container,
            parent,
            false
        )
        return FavoriteMovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteMovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount() = movies.size

    fun setData(newMovies: ArrayList<MovieBinding>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    fun deleteMovie(id: Long) {
        movies = movies.filter { it.id != id } as ArrayList<MovieBinding>
        notifyDataSetChanged()
    }

    inner class FavoriteMovieViewHolder(var view: FavoriteMovieItemContainerBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(movie: MovieBinding) {
            view.movie = movie
            view.movieClick = movieItemClick
        }
    }
}