package com.twins.testapp.ui.movieDetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twins.testapp.AppConstants.Companion.DATA_ERROR
import com.twins.testapp.AppConstants.Companion.INTERNET_ERROR
import com.twins.testapp.AppConstants.Companion.UNKNOWN_ERROR
import com.twins.testapp.data.repository.MovieRepository
import com.twins.testapp.model.AppResponse
import com.twins.testapp.model.MovieDetails
import com.twins.testapp.model.ResultWrapper
import kotlinx.coroutines.launch
import timber.log.Timber

class MovieDetailsViewModel @ViewModelInject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    val movieDetailsLiveData = MutableLiveData<AppResponse<MovieDetails>>()

    fun getDetails(id: Int) {
        viewModelScope.launch {
            when (val response = movieRepository.getMovieDetails(id)) {
                is ResultWrapper.Success -> {
                    val data = response.value
                    if (data !== null) {
                        movieDetailsLiveData.value = AppResponse.Success(data)
                    }else{
                        movieDetailsLiveData.value = AppResponse.Error(DATA_ERROR)
                    }
                }
                is ResultWrapper.NetworkError -> {
                    movieDetailsLiveData.value = AppResponse.Error(INTERNET_ERROR)
                    Timber.i("NetworkError")
                }
                is ResultWrapper.GenericError -> {
                    movieDetailsLiveData.value = AppResponse.Error(UNKNOWN_ERROR)
                    Timber.i("GenericError")
                }
            }
        }
    }
}