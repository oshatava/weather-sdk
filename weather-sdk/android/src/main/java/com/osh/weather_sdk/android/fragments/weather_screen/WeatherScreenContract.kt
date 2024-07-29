package com.osh.weather_sdk.android.fragments.weather_screen

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.osh.weather_sdk.android.fragments.weather_screen.WeatherScreenFragment.Companion.addResultListener
import com.osh.weather_sdk.common.model.NavigationEvent
import com.osh.weather_sdk.common.screen.ScreenController
import com.osh.weather_sdk.common.screen.weather_forecast.WeatherScreenContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch


internal class WeatherScreenFragmentContract(
    private val resultId: String
) : WeatherScreenContract<Fragment, Fragment> {

    override fun create(params: String): Fragment {
        return WeatherScreenFragment.newInstance(resultId, params)
    }

    override fun bind(bindContext: Fragment): ScreenController<NavigationEvent> {
        return WeatherScreenController(resultId, bindContext)
    }
}

private class WeatherScreenController(
    private val resultId: String,
    parentFragment: Fragment
) : ScreenController<NavigationEvent> {

    private val navigationEventsSharedFlow =
        MutableSharedFlow<NavigationEvent>()

    init {
        with(parentFragment) {
            addResultListener(resultId) { result ->
                viewLifecycleOwner.lifecycleScope.launch {
                    navigationEventsSharedFlow.emit(result)
                }
            }
        }
    }

    override val navigationEventsFlow: Flow<NavigationEvent>
        get() = navigationEventsSharedFlow

}
