package com.osh.weather_sdk.common.screen.common.model

/**
 * Sealed class representing the state of a loadable resource, which can be in one of the following states:
 * - Error: Represents an error state with an optional message.
 * - Load: Represents a loading state.
 * - Ready: Represents a state where data is successfully loaded.
 */
sealed class LoadableState<out DATA : Any> {
    /**
     * Represents an error state with an optional message.
     *
     * @param message The optional error message.
     */
    data class Error<out DATA : Any>(val message: String?) : LoadableState<DATA>()

    /**
     * Represents a loading state.
     */
    data object Load : LoadableState<Nothing>()

    /**
     * Represents a state where data is successfully loaded.
     *
     * @param data The successfully loaded data.
     */
    data class Ready<out DATA : Any>(val data: DATA) : LoadableState<DATA>()
}

/**
 * Extension function to convert a nullable String to an Error state.
 *
 * @return An Error state with the optional error message.
 */
fun <DATA : Any> String?.toError() = LoadableState.Error<DATA>(this)

/**
 * Extension function to convert a Throwable to an Error state.
 *
 * @return An Error state with the error message from the Throwable.
 */
fun <DATA : Any> Throwable.toError() = LoadableState.Error<DATA>(this.message)

/**
 * Extension function to convert a successfully loaded data to a Ready state.
 *
 * @return A Ready state with the successfully loaded data.
 */
fun <DATA : Any> DATA.toReady() = LoadableState.Ready(this)
