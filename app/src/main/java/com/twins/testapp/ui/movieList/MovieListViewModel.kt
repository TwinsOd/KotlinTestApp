package com.twins.testapp.ui.movieList

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.twins.testapp.data.repository.MovieRepository
import com.twins.testapp.model.Movie
import kotlinx.coroutines.flow.Flow

class MovieListViewModel @ViewModelInject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val movies: Flow<PagingData<Movie>> = Pager(PagingConfig(pageSize = 20)) {
        MoviesPagingSource(movieRepository)
    }.flow
        .cachedIn(viewModelScope)

}