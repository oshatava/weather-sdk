package com.osh.weather_sdk.common.di

/**
 * Interface representing a creator that generates an instance of type `T` based on a dependency of type `R`.
 *
 * @param R The type of the dependency required to create the instance.
 * @param T The type of the instance that is created.
 */
interface Creator<R, T> {
    /**
     * Creates an instance of type `T` based on the provided dependency of type `R`.
     *
     * @param dependency The dependency required to create the instance.
     * @return The created instance of type `T`.
     */
    fun create(dependency: R): T
}
