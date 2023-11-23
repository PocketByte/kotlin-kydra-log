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
     * In this case, only the wrapper filter will be ignored.
     * @param level Log level
     * @param tag Tag of the log record. Nullable
     * @param message Message to be written into log
     */
    abstract fun doLog(level: LogLevel, tag: String?, message: Any)

    /**
     * Writes log with provided level and tag.
     * @param level Log level
     * @param tag Tag of the log record. Nullable
     * @param message Message to be written into log
     */
    fun log(level: LogLevel, tag: String?, message: Any) {
        if (filter?.invoke(level, tag) != false) {
            doLog(level, tag, message)
        }
    }

    /**
     * Writes log with provided level and tag.
     * @param level Log level
     * @param tag Tag of the log record. Nullable
     * @param function Function that returns message to be written into log
     */
    inline fun log(level: LogLevel, tag: String?, function: () -> Any) {
        if (filter?.invoke(level, tag) != false) {
            doLog(level, tag, function())
        }
    }
}
