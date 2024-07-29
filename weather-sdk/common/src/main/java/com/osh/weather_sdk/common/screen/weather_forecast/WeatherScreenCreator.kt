package com.osh.weather_sdk.common.screen.weather_forecast

import com.osh.weather_sdk.common.model.NavigationEvent
import com.osh.weather_sdk.common.screen.ScreenContract

/**
 * Contract for the weather screen, extending the ScreenContract interface.
 *
 */
interface WeatherScreenContract<BindContext, UiScreen> :
    ScreenContract<String, BindContext, UiScreen, NavigationEvent>

