/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

class ThrowExceptionLogger(
        private val exceptionFactory: Factory
): Logger {

    class Exception(
        val logLevel: LogLevel,
        val logTag: String?,
        val logMessage: String?,
        cause: Throwable?
    ): RuntimeException(
        "level=${logLevel.name}; " +
        "tag=${logTag ?: "null"}" +
        (logMessage?.let { "; message=$it" } ?: ""),
        cause
    )

    interface Factory {
        fun exceptionFromMessage(level: LogLevel, tag: String?, message: String): Throwable
        fun exceptionFromException(level: LogLevel, tag: String?, exception: Throwable): Throwable
        fun exceptionFromFunction(level: LogLevel, tag: String?, function: () -> String): Throwable
    }

    constructor(): this(FactoryImpl())
    constructor(exceptionMessage: String): this(RuntimeException(exceptionMessage))
    constructor(exception: Throwable): this(ConstantExceptionFactory(exception))

    override fun log(level: LogLevel, tag: String?, message: String) {
        throw exceptionFactory.exceptionFromMessage(level, tag, message)
    }

    override fun log(level: LogLevel, tag: String?, exception: Throwable) {
        throw exceptionFactory.exceptionFromException(level, tag, exception)
    }

    override fun log(level: LogLevel, tag: String?, function: () -> String) {
        throw exceptionFactory.exceptionFromFunction(level, tag, function)
    }


    private class FactoryImpl: Factory {
        override fun exceptionFromMessage(level: LogLevel, tag: String?, message: String): Throwable {
            return Exception(level, tag, message, null)
        }

        override fun exceptionFromException(level: LogLevel, tag: String?, exception: Throwable): Throwable {
            return Exception(level, tag, null, exception)
        }

        override fun exceptionFromFunction(level: LogLevel, tag: String?, function: () -> String): Throwable {
            return exceptionFromMessage(level, tag, function())
        }
    }

    private class ConstantExceptionFactory(
        val exception: Throwable
    ): Factory {
        override fun exceptionFromMessage(level: LogLevel, tag: String?, message: String): Throwable {
            return this.exception
        }

        override fun exceptionFromException(level: LogLevel, tag: String?, exception: Throwable): Throwable {
            return this.exception
        }

        override fun exceptionFromFunction(level: LogLevel, tag: String?, function: () -> String): Throwable {
            return this.exception
        }
    }
}