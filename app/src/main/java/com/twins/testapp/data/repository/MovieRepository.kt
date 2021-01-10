package com.twins.testapp.data.repository

import com.twins.testapp.data.remote.api.TestService
import com.twins.testapp.helpers.safeApiCall
import com.twins.testapp.model.Movie
import com.twins.testapp.model.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val testService: TestService,
) {
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun getMovie(): ResultWrapper<List<Movie>?> {
        val apiKey = "1146110d26c9d41c58f6435077267513"
        return safeApiCall(dispatcher) {
            testService.getPopularMovies(apiKey, 1).body()?.results
        }
    }
}