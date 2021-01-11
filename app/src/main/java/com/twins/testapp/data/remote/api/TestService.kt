package com.twins.testapp.data.remote.api

import com.twins.testapp.model.MovieDetails
import com.twins.testapp.model.PopularMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TestService {
    companion object {
        const val BASE_API_URL = "https://api.themoviedb.org/"
    }

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<PopularMoviesResponse>

    @GET("/3/movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String,
    ): Response<MovieDetails>
}