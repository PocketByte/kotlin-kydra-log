/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.hydra.log

import java.lang.String.format

/**
 * iOS implementation of Logger that writes logs using NSLog.
 */
class PrintLogger: Logger {

    override fun log(level: LogLevel, tag: String?, message: String, vararg arguments: Any) {
        println(logToString(level, tag, format(message, *arguments)))
    }

    override fun log(level: LogLevel, tag: String?, exception: Throwable) {
        println(logToString(level, tag, exceptionToString(exception)))
    }

    override fun log(level: LogLevel, tag: String?, function: () -> String) {
        log(level, tag, function())
    }

    private fun logToString(level: LogLevel, tag: String?, message: String): String {
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

    private fun exceptionToString(exception: Throwable): String {
        val builder = StringBuilder()

        builder.append(exception::class.qualifiedName)

        if (exception.message != null)
            builder.append(": ").append(exception.message!!)

        exception.stackTrace.forEach {
            builder.append("\n").append(it)
        }

        return builder.toString()
    }

}
