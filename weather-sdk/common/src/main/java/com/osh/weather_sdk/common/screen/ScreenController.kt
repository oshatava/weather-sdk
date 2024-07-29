package com.osh.weather_sdk.common.screen

import kotlinx.coroutines.flow.Flow


/**
 * Interface representing a screen controller that handles navigation events.
 *
 * @param NavigationEvent The type of the navigation events.
 */
interface ScreenController<NavigationEvent> {
    /**
     * A flow of navigation events to be observed.
     */
    val navigationEventsFlow: Flow<NavigationEvent>
}

/**
 * Contract for a screen, defining the methods to bind a context and create a UI screen.
 *
 * @param Params The type of the parameters used to create the screen.
 * @param BindContext The type of the context used for binding.
 * @param UiScreen The type of the UI screen.
 * @param NavigationEvent The type of the navigation events.
 */
interface ScreenContract<Params, BindContext, UiScreen, NavigationEvent> {
    /**
     * Binds the screen with the provided context.
     *
     * @param bindContext The context to bind the screen with.
     * @return A ScreenController to handle navigation events.
     */
    fun bind(bindContext: BindContext): ScreenController<NavigationEvent>

    /**
     * Creates the UI screen with the specified parameters.
     *
     * @param params The parameters used to create the screen.
     * @return The created UI screen.
     */
    fun create(params: Params): UiScreen
}
