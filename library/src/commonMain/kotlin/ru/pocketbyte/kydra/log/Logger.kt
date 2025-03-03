/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

/**
 * Base Logger class.
 */
abstract class Logger {

    open val filter: ((level: LogLevel, tag: String?) -> Boolean)? = null

    /**
     * Writes log with provided level and tag, ignoring current logger filter.
     * Despite the fact that this method ignores the filter,
     * this rule does not apply to loggers wrapped with a wrapper.
     * In this case, the wrapper filter can be ignored.
     * @param level Log level
     * @param tag Tag of the log record. Nullable
     * @param message Message to be written into log
     */
    protected abstract fun doLog(level: LogLevel, tag: String?, message: Any)

    /**
     * Writes log with provided level and tag, using logger filter to skip some messages.
     * @param level Log level
     * @param tag Tag of the log record. Nullable
     * @param message Message to be written into log
     */
    @Deprecated(
        message = "Will be removed in future releases. Use log(LogLevel, String?, () -> Any) instead.",
        replaceWith = ReplaceWith("log(level, tag) { message }"),
        level = DeprecationLevel.WARNING
    )
    fun log(level: LogLevel, tag: String?, message: Any) {
        log(level, tag) { message }
    }

    /**
     * Writes log with provided level and tag, using logger filter to skip some messages.
     * @param level Log level
     * @param tag Tag of the log record. Nullable
     * @param function Function that returns message to be written into log
     */
    inline fun log(level: LogLevel, tag: String?, function: () -> Any) {
        if (filter?.invoke(level, tag) != false) {
            callDoLog(level, tag, function())
        }
    }

    /**
     * Writes log with provided level and tag, using logger filter to skip some messages.
     *
     * @param level Log level
     * @param tag Tag of the log record. Nullable
     * @param message Message to be written into log
     * @param omitFilter If true, filtering will be skipped.
     */
    @Deprecated(
        message = "Don't use this method. It was created for internal purposes.",
        replaceWith = ReplaceWith("log(level, tag) { message }"),
        level = DeprecationLevel.ERROR
    )
    fun log(level: LogLevel, tag: String?, message: Any, omitFilter: Boolean) {
        if (omitFilter || filter?.invoke(level, tag) != false) {
            doLog(level, tag, message)
        }
    }

    /**
     * Not for public use!!!
     */
    @PublishedApi
    internal fun callDoLog(level: LogLevel, tag: String?, message: Any) =
        doLog(level, tag, message)
}
