package com.twins.testapp.ui.movieList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.twins.testapp.R
import com.twins.testapp.model.Movie
import kotlinx.android.synthetic.main.item_movie.view.*
import timber.log.Timber

class MovieAdapter(): PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieComparator) {

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        Timber.i("onBindViewHolder ${item?.id}")

        holder.itemView.title_view.text = item?.title
        holder.itemView.overview_view.text = item?.overview
        holder.itemView.date_view.text = item?.release_date
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        Timber.i("onCreateViewHolder")
        return MovieViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie, parent, false)
        )
    }

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view)

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