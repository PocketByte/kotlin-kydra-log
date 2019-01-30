/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.hydra.log

import kotlinx.cinterop.*
import platform.Foundation.*

/**
 * iOS implementation of Logger that writes logs using NSLog.
 */
class IosLogger: Logger {

    override fun log(level: LogLevel, tag: String?, message: String, vararg arguments: Any) {
        NSLog(logToString(level, tag, format(message, *arguments), null))
    }

    override fun log(level: LogLevel, tag: String?, exception: Throwable) {
        NSLog(logToString(level, tag, null, exception))
    }

    override fun log(level: LogLevel, tag: String?, function: () -> String) {
        log(level, tag, function())
    }

    private fun logToString(level: LogLevel, tag: String?, message: String?,
                            exception: Throwable?): String {
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

        if (message?.isNotEmpty() == true)
            builder.append(message)
        else if(exception != null) {
            builder.append(exception::class.qualifiedName)

            if (exception.message != null)
                builder.append(": ").append(exception.message!!)

            exception.getStackTrace().forEach {
                builder.append("\n").append(it)
            }
        }

        return builder.toString()
    }

}
