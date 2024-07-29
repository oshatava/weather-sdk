package com.osh.weather_sdk.android.fragments.weather_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.osh.weather_sdk.android.WeatherSDK
import com.osh.weather_sdk.android.fragments.R
import com.osh.weather_sdk.android.fragments.databinding.FragmentWeatherScreenBinding
import com.osh.weather_sdk.android.fragments.utils.findOrCreateViewModel
import com.osh.weather_sdk.android.fragments.utils.formatAsTime
import com.osh.weather_sdk.android.fragments.utils.toLocalDateTime
import com.osh.weather_sdk.common.model.CurrentWeather
import com.osh.weather_sdk.common.model.ForecastHourly
import com.osh.weather_sdk.common.model.NavigationEvent
import com.osh.weather_sdk.common.screen.common.model.LoadableState
import com.osh.weather_sdk.common.screen.weather_forecast.WeatherForecastViewModelContract.UiEvent
import com.osh.weather_sdk.common.screen.weather_forecast.WeatherForecastViewModelContract.UiState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


internal class WeatherScreenFragment : Fragment() {

    companion object {
        private const val RESULT_VALUE_ARG = "WeatherScreenFragment.Result.Value"
        private const val CITY_ARG = "WeatherScreenFragment.City"
        private const val ID_ARG = "WeatherScreenFragment.Id"
        private fun resultKey(id:String) = "WeatherScreenFragment.Result.$id"

        fun newInstance(id: String, city: String) = WeatherScreenFragment().apply {
            arguments = Bundle().apply {
                putString(ID_ARG, id)
                putString(CITY_ARG, city)
            }
        }

        fun Fragment.addResultListener(id: String, onResult: (NavigationEvent) -> Unit) {
            requireActivity().supportFragmentManager.setFragmentResultListener(
                resultKey(id),
                viewLifecycleOwner
            ) { _, result ->
                val value =
                    result.getString(RESULT_VALUE_ARG) ?: error("Argument does not provided!")
                val data = Json.decodeFromString<NavigationEvent>(value)
                onResult.invoke(data)
            }
        }

        private val WeatherScreenFragment.resultId: String
            get() = requireArguments().let {
                it.getString(ID_ARG) ?: error("Argument does not provided!")
            }

        private val WeatherScreenFragment.cityName: String
            get() = requireArguments().let {
                it.getString(CITY_ARG) ?: error("Argument does not provided!")
            }
    }

    private val viewModel: WeatherScreenViewModel by lazy {
        findOrCreateViewModel(this) {
            WeatherSDK.component.provideWeatherScreenViewModelAssisted(cityName).get()
        }
    }

    private var _binding: FragmentWeatherScreenBinding? = null
    private val binding get() = _binding!!
    private val adapter = WeatherScreenAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherScreenBinding.inflate(inflater, container, false).apply {
            hourlyList.adapter = adapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            viewModel.onEvent(UiEvent.CloseEvent)
        }

        viewModel.navigationEventsFlow.onEach(::publishResult)
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.uiStateFlow.onEach(::renderState).launchIn(viewLifecycleOwner.lifecycleScope)

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.onEvent(UiEvent.CloseEvent)
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun publishResult(navigationEvent: NavigationEvent) {
        requireActivity().supportFragmentManager.setFragmentResult(resultKey(resultId), Bundle().apply {
            val v = Json.encodeToString(navigationEvent)
            putString(RESULT_VALUE_ARG, v)
        })
    }

    private fun renderState(state: UiState) {
        renderCity(state.currentWeather)
        renderHourly(state.hourly)
    }

    private fun renderCity(city: LoadableState<CurrentWeather>) = with(binding) {
        when (city) {
            is LoadableState.Error -> {
                cityLoading.isVisible = false
            }

            LoadableState.Load -> {
                cityLoading.isVisible = true
            }

            is LoadableState.Ready -> {
                cityLoading.isVisible = false
                cityName.text = city.data.location
                cityTemperature.text = resources.getString(
                    R.string.weather_screen_city_temperature_template,
                    city.data.temperature
                )
                cityWeatherDescription.text = city.data.description
                cityWeatherTime.text = resources.getString(
                    R.string.weather_screen_city_date_template,
                    city.data.timestampLocal.toLocalDateTime().formatAsTime()
                )
            }
        }
    }

    private fun renderHourly(hourly: LoadableState<ForecastHourly>) = with(binding) {
        when (hourly) {
            is LoadableState.Error -> {
                hourlyLoading.isVisible = false
            }

            LoadableState.Load -> {
                hourlyLoading.isVisible = true
            }

            is LoadableState.Ready -> {
                hourlyLoading.isVisible = false
                adapter.submitList(hourly.data.hourly)
            }
        }
    }
}
