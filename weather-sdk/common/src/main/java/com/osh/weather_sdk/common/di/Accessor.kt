package com.osh.weather_sdk.common.di

/**
 * Interface representing an accessor that retrieves or creates an instance of a type `T` based on a request of type `R`.
 *
 * @param R The type of the request.
 * @param T The type of the result that is retrieved or created.
 */
interface Accessor<R, T> {
    /**
     * Retrieves an existing instance or creates a new instance of type `T` based on the provided request of type `R`.
     *
     * @param request The request based on which the instance is retrieved or created.
     * @return The retrieved or newly created instance of type `T`.
     */
    fun getOrCreate(request: R): T
}
