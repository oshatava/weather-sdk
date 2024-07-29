package com.osh.weather_sdk.common.screen.hourly_forecast.impl

import app.cash.turbine.test
import com.osh.weather_sdk.common.logger.LoggerBuilder
import com.osh.weather_sdk.common.model.ApiResult
import com.osh.weather_sdk.common.model.ForecastHourly
import com.osh.weather_sdk.common.model.NavigationEvent
import com.osh.weather_sdk.common.screen.hourly_forecast.HourlyForecastViewModelContract
import com.osh.weather_sdk.common.source.HourlyForecastWeatherSource
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
internal class HourlyForecastViewModelDelegateImplTest {

    private val testCity = "test"
    private val testHours = 24

    private val sourceMockk = mockk<HourlyForecastWeatherSource>(relaxed = true){
        coEvery { requestForecastHourly(any(), any()) } returns mockk()
    }

    private val loggerMock = mockk<LoggerBuilder>(){
        every { tag(any()) } returns mockk(relaxed = true)
    }

    @Test
    fun `Given bind to ViewModel, When try to send an InitEvent, Then proper source method called`() = runTest {
        val underTest = HourlyForecastViewModelDelegateImpl(
            source = sourceMockk,
            logger = loggerMock
        )

        underTest.bindTo(this)

        underTest.onEvent(HourlyForecastViewModelContract.UiEvent.InitEvent(testCity, testHours))

        runCurrent()

        coVerify { sourceMockk.requestForecastHourly(eq(testCity), eq(testHours)) }
    }

    @Test
    fun `Given bind to ViewModel, When try to send an InitEvent, And source returns an error, Then proper state on navigation`() = runTest {

        coEvery { sourceMockk.requestForecastHourly(any(), any()) } returns ApiResult.Error<ForecastHourly>("")

        val underTest = HourlyForecastViewModelDelegateImpl(
            source = sourceMockk,
            logger = loggerMock
        )

        underTest.bindTo(this.backgroundScope)

        underTest.onEvent(HourlyForecastViewModelContract.UiEvent.InitEvent(testCity, testHours))

        underTest.navigationEventsFlow.test{
            assertEquals(NavigationEvent.OnFinishedWithError, awaitItem())
        }
    }

    @Test
    fun `Given not bind to ViewModel, When try to send an InitEvent, Then exception thrown`() = runTest {
        val underTest = HourlyForecastViewModelDelegateImpl(
            source = sourceMockk,
            logger = loggerMock
        )

        Assert.assertThrows(IllegalStateException::class.java){
            underTest.onEvent(HourlyForecastViewModelContract.UiEvent.InitEvent(testCity, testHours))
        }

        runCurrent()
    }

}