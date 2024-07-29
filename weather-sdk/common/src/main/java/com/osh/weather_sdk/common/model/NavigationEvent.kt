package com.osh.weather_sdk.common.model

import kotlinx.serialization.Serializable

/**
 * Sealed class representing navigation events, which can be serialized.
 */
@Serializable
sealed class NavigationEvent {

    /**
     * Navigation event indicating that a screen has finished successfully.
     */
    @Serializable
    data object OnFinished : NavigationEvent()

    /**
     * Navigation event indicating that a screen has finished with an error.
     */
    @Serializable
    data object OnFinishedWithError : NavigationEvent()
}
