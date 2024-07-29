package com.osh.ginisample.welcome_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.osh.ginisample.databinding.FragmentWelcomeBinding
import com.osh.weather_sdk.android.WeatherSDK
import com.osh.weather_sdk.common.model.NavigationEvent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!
    private val weatherScreenContract by lazy { WeatherSDK.createWeatherScreenContract() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weatherScreenContract.bind(this).navigationEventsFlow
            .onEach(::processWeatherNavigationEvent)
            .launchIn(viewLifecycleOwner.lifecycleScope)

        binding.buttonFirst.setOnClickListener {
            showWeatherFragment()
        }
    }

    private fun showWeatherFragment() {
        val fragment = weatherScreenContract.create(binding.cityNameInputField.text.toString())
        childFragmentManager.beginTransaction()
            .add(binding.fragmentContainer.id, fragment, FRAGMENT_TAG)
            .commit()
    }

    private fun processWeatherNavigationEvent(event: NavigationEvent) {
        when (event) {
            NavigationEvent.OnFinished -> {
                closeWeatherSDKScreen()
            }

            NavigationEvent.OnFinishedWithError -> {
                Toast.makeText(
                    requireContext(),
                    "Error occurred in the Weather SDK",
                    Toast.LENGTH_SHORT
                ).show()
                closeWeatherSDKScreen()
            }
        }
    }

    private fun closeWeatherSDKScreen(){
        childFragmentManager.findFragmentByTag(FRAGMENT_TAG)?.let {
            childFragmentManager.beginTransaction().remove(it).commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FRAGMENT_TAG = "WelcomeFragment.WeatherFragment"
    }
}