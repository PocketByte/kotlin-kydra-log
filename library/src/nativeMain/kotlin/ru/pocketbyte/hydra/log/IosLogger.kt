/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.hydra.log

import platform.Foundation.*
import ru.pocketbyte.hydra.log.format.*

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

    private fun format(format: String, vararg arguments: Any): String {
        return when(arguments.size) {
            0 -> format
            1 -> ns_format(format,
                    arguments[0])!!
            2 -> ns_format(format,
                    arguments[0],
                    arguments[1])!!
            3 -> ns_format(format,
                    arguments[0],
                    arguments[1],
                    arguments[2])!!
            4 -> ns_format(format,
                    arguments[0],
                    arguments[1],
                    arguments[2],
                    arguments[3])!!
            5 -> ns_format(format,
                    arguments[0],
                    arguments[1],
                    arguments[2],
                    arguments[3],
                    arguments[4])!!
            6 -> ns_format(format,
                    arguments[0],
                    arguments[1],
                    arguments[2],
                    arguments[3],
                    arguments[4],
                    arguments[5])!!
            7 -> ns_format(format,
                    arguments[0],
                    arguments[1],
                    arguments[2],
                    arguments[3],
                    arguments[4],
                    arguments[5],
                    arguments[6])!!
            8 -> ns_format(format,
                    arguments[0],
                    arguments[1],
                    arguments[2],
                    arguments[3],
                    arguments[4],
                    arguments[5],
                    arguments[6],
                    arguments[7])!!
            9 -> ns_format(format,
                    arguments[0],
                    arguments[1],
                    arguments[2],
                    arguments[3],
                    arguments[4],
                    arguments[5],
                    arguments[6],
                    arguments[7],
                    arguments[8])!!
            else -> ns_format(format,
                    arguments[0],
                    arguments[1],
                    arguments[2],
                    arguments[3],
                    arguments[4],
                    arguments[5],
                    arguments[6],
                    arguments[7],
                    arguments[8],
                    arguments[9])!!
        }
    }

}
