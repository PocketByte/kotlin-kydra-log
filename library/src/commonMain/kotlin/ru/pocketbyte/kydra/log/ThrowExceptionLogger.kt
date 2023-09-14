/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

class ThrowExceptionLogger(
        private val exceptionFactory: Factory
): Logger() {

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
        fun exceptionFromMessage(level: LogLevel, tag: String?, message: Any): Throwable
    }

    constructor(): this(FactoryImpl())
    constructor(exceptionMessage: String): this(RuntimeException(exceptionMessage))
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