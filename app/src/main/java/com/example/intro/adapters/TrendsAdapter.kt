package com.example.intro.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BaseObservable
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.intro.R
import com.example.intro.databinding.TrendsItemContainerBinding
import com.example.intro.ui.actions.FavoriteMovieClick
import com.example.intro.ui.actions.MovieItemClick
import com.example.presentation.binding.MovieBinding

class TrendsAdapter(
    private val favoriteMovieClick: FavoriteMovieClick,
    private val movieItemClick: MovieItemClick
) :
    PagedListAdapter<MovieBinding, TrendsAdapter.TrendViewHolder>(object :
            DiffUtil.ItemCallback<MovieBinding>() {
        override fun areItemsTheSame(oldItem: MovieBinding, newItem: MovieBinding) =
            oldItem.favorite == newItem.favorite

        override fun areContentsTheSame(oldItem: MovieBinding, newItem: MovieBinding) =
            oldItem.id == newItem.id
    }) {

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

    override fun onBindViewHolder(holder: TrendViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    fun favoriteMovie(movie: MovieBinding) {
        movie.favorite
        // notifyDataSetChanged()
    }

    inner class TrendViewHolder(var view: TrendsItemContainerBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(movie: MovieBinding) {
            view.movie = movie
            view.favorite = movie.favorite
            view.executePendingBindings()
            view.favoriteClick = favoriteMovieClick
            view.movieItemClick = movieItemClick
        }
    }

}