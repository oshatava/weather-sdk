package com.osh.weather_sdk.logger.impl

import android.util.Log
import com.osh.weather_sdk.common.logger.Logger
import com.osh.weather_sdk.common.logger.LoggerBuilder

private const val SDK_TAG_PREFIX = "WSDK:"

internal class LoggerBuilderImpl: LoggerBuilder {
    override fun tag(tag: String): Logger {
        return LoggerImpl("$SDK_TAG_PREFIX$tag")
    }
}

private class LoggerImpl(
    private val tag: String
): Logger {
    override fun d(message: () -> String) {
        Log.d(tag, message())
    }

    override fun w(message: () -> String) {
        Log.w(tag, message())
    }

    override fun i(message: () -> String) {
        Log.i(tag, message())
    }

    override fun e(message: () -> String) {
        Log.e(tag, message())
    }

}