package com.twins.testapp.ui.movieList

import androidx.paging.PagingSource
import com.twins.testapp.AppConstants.Companion.UNKNOWN_ERROR
import com.twins.testapp.data.repository.MovieRepository
import com.twins.testapp.model.Movie
import com.twins.testapp.model.ResultWrapper
import timber.log.Timber

class MoviesPagingSource(
    private val movieRepository: MovieRepository
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        Timber.i("loading")
        try {
            val nextPage = params.key ?: 1
            Timber.i("loading $nextPage")

            when (val response = movieRepository.getMovies(nextPage)) {
                is ResultWrapper.Success -> {
                    val data = response.value
                    Timber.i("list size ${data?.results?.size}")
                    data?.let {
                        return LoadResult.Page(
                            data = it.results,
                            prevKey = if (nextPage == 1) null else nextPage - 1,
                            nextKey = data.page + 1
                        )
                    }
                }
                is ResultWrapper.NetworkError -> {
                    Timber.i("NetworkError")
                }
                is ResultWrapper.GenericError -> {
                    Timber.i("GenericError")
                }
            }

            return LoadResult.Error(Exception(UNKNOWN_ERROR))
        } catch (e: Exception) {
            Timber.e(e)
            return LoadResult.Error(e)
        }
    }
}