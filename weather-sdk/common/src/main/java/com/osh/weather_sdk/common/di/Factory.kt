package com.osh.weather_sdk.common.di

internal class FactoryProvider<T>(
    private val instanceCreator: ()->T
):Provider<T> {
    override fun get(): T {
        return instanceCreator()
    }
}

/**
 * Creates a [Provider] instance using a factory function.
 *
 * @param instanceCreator A lambda function that creates an instance of type `T`.
 * @return A [Provider] that provides instances of type `T` using the given factory function.
 */
fun <T> factory(instanceCreator: () -> T): Provider<T> = FactoryProvider(instanceCreator)
