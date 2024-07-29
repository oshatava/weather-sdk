package com.osh.weather_sdk.common.di

internal class MapProvider<T,Key>(
    private val instanceCreator: (Key)->T
):Accessor<Key, Provider<T>> {
    private val providersMap = mutableMapOf<Key, Provider<T>>()
    override fun getOrCreate(request: Key): Provider<T> {
        return providersMap.getOrPut(request) { single { instanceCreator(request) } }
    }
}

/**
 * Creates an [Accessor] instance that maps keys to providers using a provided instance creation function.
 *
 * @param instanceCreator A lambda function that maps a `Key` to an instance of type `T`.
 * @return An [Accessor] that provides [Provider]s of type `T` based on the provided key.
 */
fun <Key, T> map(instanceCreator: (Key) -> T): Accessor<Key, Provider<T>> = MapProvider(instanceCreator)
