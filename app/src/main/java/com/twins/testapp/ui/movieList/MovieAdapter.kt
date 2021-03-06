package com.twins.testapp.ui.movieList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.twins.testapp.R
import com.twins.testapp.databinding.ItemMovieBinding
import com.twins.testapp.model.Movie
import timber.log.Timber

class MovieAdapter(
    private val onClickListener: View.OnClickListener
) : PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieComparator) {

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
            holder.itemView.tag = it.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        Timber.i("onCreateViewHolder")
        return MovieViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie, parent, false),
            onClickListener
        )
    }

    class MovieViewHolder(view: View, onClickListener: View.OnClickListener) :
        RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener(onClickListener)
        }

        private val binding = ItemMovieBinding.bind(view)

        fun bind(movie: Movie) {
            with(binding) {
                titleView.text = movie.title
                overviewView.text = movie.overview
                dateView.text = movie.release_date
            }
        }
    }

    object MovieComparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}