# Weather SDK Integration Guide

This guide provides step-by-step instructions to integrate the Weather SDK into your Android application. By following these instructions, you will be able to configure the Weather SDK to retrieve weather information using the WeatherBit API.

## Prerequisites

1. **Android Studio**: Make sure you have Android Studio installed on your machine.
2. **Weather SDK**: Ensure that you have access to the Weather SDK library files or repository.
3. **API Key**: Obtain an API key from WeatherBit.

## Step-by-Step Integration

### 1. Import Necessary Classes

In the file where you plan to initialize the Weather SDK, import the necessary classes:

```kotlin
import com.osh.weather_sdk.android.WeatherSDK
import com.osh.weather_sdk.logger.WeatherAndroidLogger
import com.osh.weather_sdk.source.weatherbit.WeatherBitSourceProvider
import com.osh.weather_sdk.source.weatherbit.WeatherSourceConfig
```

### 2. Initialize the Weather SDK in the Application Class
Create a configuration object using your WeatherBit API key, set up the logger, and initialize the Weather SDK. This should be done in your Application class to ensure it's available throughout your app's lifecycle.

Create or update your Application class as follows:

```kotlin

    override fun onCreate() {
        super.onCreate()
        .....
        // Initialize Weather SDK
        val config = WeatherSourceConfig(apiKey = "<api key>")
        val loggerBuilder = WeatherAndroidLogger.create()
        val source = WeatherBitSourceProvider.create(loggerBuilder).source { config }
        
        WeatherSDK.create(
            weatherSourceProvider = source,
            loggerBuilder = loggerBuilder
        )
        .....
    }
```

### 3. Creating the Weather Screen and Handling Notification Events
You can create a screen to display weather data and handle navigation events using the Weather SDK. 

Example in Fragment:

```kotlin
    
    // access to the weatherScreenContract
    private val weatherScreenContract by lazy { WeatherSDK.createWeatherScreenContract() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        .........
        // Bind to navigation events
        weatherScreenContract.bind(this).navigationEventsFlow
            .onEach(::processWeatherNavigationEvent)
            .launchIn(viewLifecycleOwner.lifecycleScope)
        ........
    }

    // Handle navigation events
    private fun processWeatherNavigationEvent(event: NavigationEvent) {
        when (event) {
            NavigationEvent.OnFinished -> {
                // Close fragment here
            }

            NavigationEvent.OnFinishedWithError -> {
                // Close fragment and notify about error
            }
        }
    }
    
    // Create fragmnet instance and show
    private fun showWeatherScreen(cityName: String) {
        // obtain fragment from contract
        val weatherScreenFragment = weatherScreenContract.create(cityName)
        
        // add/replace to the FragmentManager or use in the navigation frameworks
    }
```

## Documetation
You can generate documentation using the following command:

```sh
gradle weather-sdk:dokkaHtmlMultiModule
```
The documentation will be created and stored in the weather-sdk/build/dokka/htmlMultiModule directory.

## Tests
Several tests have been created, primarily for the common module. To run these tests, use the following command:

```sh
gradle test
```

 ## Project Structure

- **app**: The demo app module.
- **weather-sdk**: The root directory for SDK modules.
  - **weather-sdk:common**: Common JVM module containing the implementation of screen domain and definitions for common classes/interfaces, such as logger and data sources.
  - **weather-sdk:logger**: Android module implementing logging functionality.
  - **weather-sdk:source-weatherbit**: Android module implementing the data source.
  - **weather-sdk:android**: Android module that includes fragment screen implementations and serves as the entry point for SDK components.