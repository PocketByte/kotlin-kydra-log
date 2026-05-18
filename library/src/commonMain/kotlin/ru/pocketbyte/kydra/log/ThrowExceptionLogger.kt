/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

/**
 * A logger that throws an exception on every log call instead of writing to a log output.
 * Intended for use in tests to assert that no unwanted log records are produced.
 *
 * The exception is created by a [Factory], which can be customized via the available constructors.
 */
class ThrowExceptionLogger(
    private val exceptionFactory: Factory
): Logger() {

    /**
     * Exception thrown by [ThrowExceptionLogger] when using the default [Factory].
     * Carries the details of the log call that triggered the exception.
     */
    class Exception(
        /** The log level of the record that triggered this exception. */
        val logLevel: LogLevel,
        /** The tag of the log record that triggered this exception. Nullable. */
        val logTag: String?,
        /** The text message of the log record, or `null` if the message was a [Throwable]. */
        val logMessage: String?,
        cause: Throwable?
    ): RuntimeException(
        "level=${logLevel.name}; " +
        "tag=${logTag ?: "null"}" +
        (logMessage?.let { "; message=$it" } ?: ""),
        cause
    )

    /**
     * Factory that creates a [Throwable] from a log record.
     * Implement this interface to provide a custom exception strategy for [ThrowExceptionLogger].
     */
    interface Factory {
        /**
         * Creates a [Throwable] for the given log record.
         * @param level Log level of the record
         * @param tag Tag of the log record. Nullable
         * @param message Message of the log record
         * @return The exception to be thrown
         */
        fun exceptionFromMessage(level: LogLevel, tag: String?, message: Any): Throwable
    }

    /** Creates a logger that throws [Exception] with the details of each log call. */
    constructor(): this(FactoryImpl())

    /** Creates a logger that always throws a [RuntimeException] with the given [exceptionMessage]. */
    constructor(exceptionMessage: String): this(RuntimeException(exceptionMessage))

    /** Creates a logger that always throws the provided [exception] instance. */
    constructor(exception: Throwable): this(ConstantExceptionFactory(exception))

    override fun doLog(level: LogLevel, tag: String?, message: Any) {
        throw exceptionFactory.exceptionFromMessage(level, tag, message)
    }

    private class FactoryImpl: Factory {
        override fun exceptionFromMessage(level: LogLevel, tag: String?, message: Any): Throwable {
            return when(message) {
                is Throwable -> Exception(level, tag, null, message)
                else -> Exception(level, tag, message.toString(), null)
            }
        }
    }

    private class ConstantExceptionFactory(
        val exception: Throwable
    ): Factory {
        override fun exceptionFromMessage(level: LogLevel, tag: String?, message: Any): Throwable {
            return this.exception
        }
    }
}
