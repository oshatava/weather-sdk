package com.osh.weather_sdk.common.logger

/**
 * Interface representing a logger with different log levels.
 */
interface Logger {
    /**
     * Logs a debug message.
     *
     * @param message A lambda function that returns the message to be logged.
     */
    fun d(message: () -> String)

    /**
     * Logs a warning message.
     *
     * @param message A lambda function that returns the message to be logged.
     */
    fun w(message: () -> String)

    /**
     * Logs an informational message.
     *
     * @param message A lambda function that returns the message to be logged.
     */
    fun i(message: () -> String)

    /**
     * Logs an error message.
     *
     * @param message A lambda function that returns the message to be logged.
     */
    fun e(message: () -> String)
}

/**
 * Interface for building loggers with specific tags.
 */
interface LoggerBuilder {
    /**
     * Creates a logger with the specified tag.
     *
     * @param tag The tag to be associated with the logger.
     * @return A logger with the specified tag.
     */
    fun tag(tag: String): Logger
}
