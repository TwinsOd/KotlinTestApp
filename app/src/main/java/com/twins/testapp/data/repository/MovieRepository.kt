package com.twins.testapp.data.repository

import com.twins.testapp.BuildConfig
import com.twins.testapp.data.remote.api.TestService
import com.twins.testapp.helpers.safeApiCall
import com.twins.testapp.model.MovieDetails
import com.twins.testapp.model.PopularMoviesResponse
import com.twins.testapp.model.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val testService: TestService,
) {
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
    private val apiKey: String by lazy { BuildConfig.API_KEY }

    suspend fun getMovies(page: Int): ResultWrapper<PopularMoviesResponse?> {
        return safeApiCall(dispatcher) {
            testService.getPopularMovies(apiKey, page).body()
        }
    }

    suspend fun getMovieDetails(id: Int): ResultWrapper<MovieDetails?> {
        return safeApiCall(dispatcher) {
            testService.getMovieDetails(id, apiKey).body()
        }
    }
}