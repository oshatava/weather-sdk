package com.osh.weather_sdk.common.screen.current_weather.impl

import app.cash.turbine.test
import com.osh.weather_sdk.common.logger.LoggerBuilder
import com.osh.weather_sdk.common.model.ApiResult
import com.osh.weather_sdk.common.model.CurrentWeather
import com.osh.weather_sdk.common.model.NavigationEvent
import com.osh.weather_sdk.common.screen.current_weather.CurrentWeatherViewModelContract
import com.osh.weather_sdk.common.source.CurrentWeatherSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
internal class CurrentWeatherViewModelDelegateImplTest {

    private val testCity = "test"

    private val sourceMockk = mockk<CurrentWeatherSource>(relaxed = true){
        coEvery { requestCurrent(any()) } returns mockk()
    }

    private val loggerMock = mockk<LoggerBuilder>(){
        every { tag(any()) } returns mockk(relaxed = true)
    }

    @Test
    fun `Given bind to ViewModel, When try to send an InitEvent, Then proper source method called`() = runTest {
        val underTest = CurrentWeatherViewModelDelegateImpl(
            source = sourceMockk,
            logger = loggerMock
        )

        underTest.bindTo(this)

        underTest.onEvent(CurrentWeatherViewModelContract.UiEvent.InitEvent(testCity))

        runCurrent()

        coVerify { sourceMockk.requestCurrent(eq(testCity)) }
    }

    @Test
    fun `Given bind to ViewModel, When try to send an InitEvent, And source returns an error, Then proper state on navigation`() = runTest {

        coEvery { sourceMockk.requestCurrent(any()) } returns ApiResult.Error<CurrentWeather>("")

        val underTest = CurrentWeatherViewModelDelegateImpl(
            source = sourceMockk,
            logger = loggerMock
        )

        underTest.bindTo(this.backgroundScope)

        underTest.onEvent(CurrentWeatherViewModelContract.UiEvent.InitEvent(testCity))

        underTest.navigationEventsFlow.test{
            assertEquals(NavigationEvent.OnFinishedWithError, awaitItem())
        }
    }

    @Test
    fun `Given not bind to ViewModel, When try to send an InitEvent, Then exception thrown`() = runTest {
        val underTest = CurrentWeatherViewModelDelegateImpl(
            source = sourceMockk,
            logger = loggerMock
        )

        Assert.assertThrows(IllegalStateException::class.java){
            underTest.onEvent(CurrentWeatherViewModelContract.UiEvent.InitEvent(testCity))
        }

        runCurrent()
    }

}