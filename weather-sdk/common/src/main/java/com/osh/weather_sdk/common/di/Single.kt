package com.osh.weather_sdk.common.di

internal class SingleProvider<T>(
    private val instanceCreator: ()->T
):Provider<T> {
    private val instance: T by lazy(instanceCreator)
    override fun get(): T {
        return instance
    }
}

/**
 * Creates a [Provider] instance that supplies a single instance of type `T` using a provided instance creation function.
 *
 * @param instanceCreator A lambda function that creates an instance of type `T`.
 * @return A [Provider] that provides the single instance of type `T` created by the given factory function.
 */
fun <T> single(instanceCreator: () -> T): Provider<T> = SingleProvider(instanceCreator)
