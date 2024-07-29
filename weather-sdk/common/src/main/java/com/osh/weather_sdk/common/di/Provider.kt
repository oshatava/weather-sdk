package com.osh.weather_sdk.common.di

/**
 * Interface representing a provider that supplies instances of type `T`.
 *
 * @param T The type of the instance provided by this provider.
 */
interface Provider<T> {
    /**
     * Retrieves an instance of type `T`.
     *
     * @return An instance of type `T`.
     */
    fun get(): T
}
