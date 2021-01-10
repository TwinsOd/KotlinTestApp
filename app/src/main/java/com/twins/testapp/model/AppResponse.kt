package com.twins.testapp.model

sealed class AppResponse<T> {
    data class Success<T>(val data: T) : AppResponse<T>()

    data class Error<T>(val errorResponse: String?) : AppResponse<T>()

    companion object {

        /**
         * Returns [AppResponse.Success] instance.
         * @param data Data to emit with status.
         */
        fun <T> success(data: T?) = Success(data)

        /**
         * Returns [AppResponse.Error] instance.
         * @param errorResponse Description of failure.
         */
        fun <T> error(errorResponse: String?) = Error<T>(errorResponse)
    }
}