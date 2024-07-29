package com.osh.weather_sdk.common.model

/**
 * Sealed class representing the result of an API call, which can either be a success or an error.
 */
sealed class ApiResult<out DATA : Any> {
    /**
     * Represents an error result of an API call with an optional error message.
     *
     * @param message The optional error message.
     */
    data class Error<out DATA : Any>(val message: String?) : ApiResult<DATA>()

    /**
     * Represents a successful result of an API call with the retrieved data.
     *
     * @param data The successfully retrieved data.
     */
    data class Success<out DATA : Any>(val data: DATA) : ApiResult<DATA>()
}

/**
 * Extension function to convert a nullable String to an error result.
 *
 * @return An error result with the optional error message.
 */
fun <DATA : Any> String?.toError() = ApiResult.Error<DATA>(this)

/**
 * Extension function to convert a Throwable to an error result.
 *
 * @return An error result with the error message from the Throwable.
 */
fun <DATA : Any> Throwable.toError() = ApiResult.Error<DATA>(this.message)

/**
 * Extension function to convert a successfully retrieved data to a success result.
 *
 * @return A success result with the retrieved data.
 */
fun <DATA : Any> DATA.toSuccess() = ApiResult.Success(this)
