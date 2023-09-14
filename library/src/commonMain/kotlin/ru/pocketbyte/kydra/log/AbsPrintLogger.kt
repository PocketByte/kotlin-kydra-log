/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

/**
 * Base implementation of PrintLogger.
 */
abstract class AbsPrintLogger: AbsLogger() {

    protected abstract fun printLog(message: String)

    /**
     * Gets stack trace string from Throwable
     */
    protected abstract fun stackTrace(exception: Throwable): String

    /**
     * Gets qualified name from Throwable
     */
    protected abstract fun qualifiedName(exception: Throwable): String

    override fun doLog(level: LogLevel, tag: String?, string: String) {
        printLog(logToString(level, tag, string))
    }

    override fun doLog(level: LogLevel, tag: String?, exception: Throwable) {
        if (exception.message != null) {
            printLog(logToString(level, tag, "${qualifiedName(exception)}: " +
                    "${exception.message ?: ""}\n${stackTrace(exception)}"))
        } else {
            printLog(logToString(level, tag, "${qualifiedName(exception)}\n${stackTrace(exception)}"))
        }
    }

    protected open fun logToString(level: LogLevel, tag: String?, message: String): String {
        val builder = StringBuilder()

        builder.append(
                when(level) {
                    LogLevel.INFO -> "I"
                    LogLevel.DEBUG -> "D"
                    LogLevel.WARNING -> "W"
                    LogLevel.ERROR -> "E"
                }
        )

        builder.append("/")

        if (tag?.isNotEmpty() == true)
            builder.append(tag)

        builder.append(": ")
        builder.append(message)

        return builder.toString()
    }
}