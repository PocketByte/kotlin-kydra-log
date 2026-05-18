/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

import kotlin.js.JsName

/**
 * Base class for all loggers. Defines the core logging contract.
 *
 * Subclasses must implement [doLog] to handle the actual log output.
 * Filtering is applied in [log] before [doLog] is called, so [doLog]
 * implementations do not need to check the filter themselves.
 *
 * To compose multiple loggers or add filtering and tag-transform behavior,
 * use the wrapper classes from the `wrapper` and `collection` packages.
 */
abstract class Logger {

    /**
     * Optional filter that controls which log records this logger processes.
     * Returns `true` if a record with the given [LogLevel] and tag should be logged,
     * or `false` to suppress it. When `null`, all records are passed through.
     */
    open val filter: ((level: LogLevel, tag: String?) -> Boolean)? = null

    /**
     * Writes a log record with the given level and tag, bypassing this logger's [filter].
     * Note: if this logger is wrapped by another logger (e.g. `FilteredLoggerWrapper`),
     * the wrapper's own filter is still applied before this method is called.
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
        level = DeprecationLevel.ERROR
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
    inline fun log(level: LogLevel, tag: String?, crossinline function: () -> Any) {
        if (filter?.invoke(level, tag) != false) {
            callDoLog(level, tag, function())
        }
    }

    /**
     * Not for public use!!!
     */
    @PublishedApi
    @JsName("callDoLog")
    internal fun callDoLog(level: LogLevel, tag: String?, message: Any) =
        doLog(level, tag, message)
}
