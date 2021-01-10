package com.twins.testapp.model

import com.twins.testapp.AppConstants.Companion.DATA_ERROR
import com.twins.testapp.AppConstants.Companion.INTERNET_ERROR
import com.twins.testapp.AppConstants.Companion.UNKNOWN_ERROR

sealed class ResultWrapper<out T> {
    data class Success<out T>(
        val value: T
    ) : ResultWrapper<T>()

    data class GenericError(
        val code: Int? = null, val error: ErrorResponse? = null
    ) : ResultWrapper<Nothing>()

    object NetworkError : ResultWrapper<Nothing>()

    companion object {
        fun tokenError() = GenericError(501, ErrorResponse(message = INTERNET_ERROR))
        fun unknownError() = GenericError(502, ErrorResponse(message = UNKNOWN_ERROR))
        fun dataError() = GenericError(503, ErrorResponse(message = DATA_ERROR))
    }
}