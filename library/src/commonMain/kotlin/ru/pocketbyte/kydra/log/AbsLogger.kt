/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

/**
 * A convenience base class that dispatches the generic [Any] message to typed [doLog]
 * overloads for [String], [Throwable], and [ThrowableWithMessage], so subclasses do not
 * need to perform type checks themselves.
 *
 * Subclasses must implement [doLog] for [String] and [Throwable]. The [ThrowableWithMessage]
 * overload has a default implementation that falls back to [ThrowableWithMessage.toString].
 */
abstract class AbsLogger: Logger() {

    /**
     * Dispatches [message] to the appropriate typed [doLog] overload based on its runtime type.
     * @param level Log level
     * @param tag Tag of the log record. Nullable
     * @param message Message to be written into log
     */
    override fun doLog(level: LogLevel, tag: String?, message: Any) {
        when(message) {
            is ThrowableWithMessage -> doLog(level, tag, message)
            is Throwable -> doLog(level, tag, exception = message)
            else -> doLog(level, tag, string = message.toString())
        }
    }

    /**
     * Writes a plain-text log record.
     * @param level Log level
     * @param tag Tag of the log record. Nullable
     * @param string Message to be written into log
     */
    protected abstract fun doLog(level: LogLevel, tag: String?, string: String)

    /**
     * Writes a log record for an exception.
     * @param level Log level
     * @param tag Tag of the log record. Nullable
     * @param exception Exception to be written into log
     */
    protected abstract fun doLog(level: LogLevel, tag: String?, exception: Throwable)

    /**
     * Writes a log record for an exception paired with a message.
     * @param level Log level
     * @param tag Tag of the log record. Nullable
     * @param throwableWithMessage Exception and message to be written into log
     */
    protected open fun doLog(level: LogLevel, tag: String?, throwableWithMessage: ThrowableWithMessage) {
        doLog(level, tag, string = throwableWithMessage.toString())
    }
}
