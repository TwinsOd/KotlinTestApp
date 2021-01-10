package com.twins.testapp.data.remote.api

import com.twins.testapp.model.PopularMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TestService {
    companion object {
        const val BASE_API_URL = "https://api.themoviedb.org/3/"
    }

    @GET("/movie/popular")
//    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<PopularMoviesResponse>
}