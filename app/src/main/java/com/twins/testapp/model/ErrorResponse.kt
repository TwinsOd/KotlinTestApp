package com.twins.testapp.model

data class ErrorResponse(
    val error_description: String? = null, // this is the translated error shown to the user directly from the API
    val message: String?,
    val causes: Map<String, String>? = emptyMap() //this is for errors on specific field on a form
)