/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.hydra.log

import platform.Foundation.*

/**
 * iOS implementation of Logger that writes logs using NSLog.
 */
class NSLogger: Logger {

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
        val macOsFormat = format.replace("%s", "%@")

        // Variadic function not well supported yet. Temporary workaround
        return when(arguments.size) {
            0 -> macOsFormat
            1 -> NSString.create(format = macOsFormat,
                    args = *arrayOf(
                        arguments[0].toString() as NSString
                    )) as String
            2 -> NSString.create(format = macOsFormat,
                    args = *arrayOf(
                        arguments[0].toString() as NSString,
                        arguments[1].toString() as NSString
                    )) as String
            3 -> NSString.create(format = macOsFormat,
                    args = *arrayOf(
                        arguments[0].toString() as NSString,
                        arguments[1].toString() as NSString,
                        arguments[2].toString() as NSString
                    )) as String
            4 -> NSString.create(format = macOsFormat,
                    args = *arrayOf(
                        arguments[0].toString() as NSString,
                        arguments[1].toString() as NSString,
                        arguments[2].toString() as NSString,
                        arguments[3].toString() as NSString
                    )) as String
            5 -> NSString.create(format = macOsFormat,
                    args = *arrayOf(
                        arguments[0].toString() as NSString,
                        arguments[1].toString() as NSString,
                        arguments[2].toString() as NSString,
                        arguments[3].toString() as NSString,
                        arguments[4].toString() as NSString
                    )) as String
            6 -> NSString.create(format = macOsFormat,
                    args = *arrayOf(
                        arguments[0].toString() as NSString,
                        arguments[1].toString() as NSString,
                        arguments[2].toString() as NSString,
                        arguments[3].toString() as NSString,
                        arguments[4].toString() as NSString,
                        arguments[5].toString() as NSString
                    )) as String
            7 -> NSString.create(format = macOsFormat,
                    args = *arrayOf(
                        arguments[0].toString() as NSString,
                        arguments[1].toString() as NSString,
                        arguments[2].toString() as NSString,
                        arguments[3].toString() as NSString,
                        arguments[4].toString() as NSString,
                        arguments[5].toString() as NSString,
                        arguments[6].toString() as NSString
                    )) as String
            8 -> NSString.create(format = macOsFormat,
                    args = *arrayOf(
                        arguments[0].toString() as NSString,
                        arguments[1].toString() as NSString,
                        arguments[2].toString() as NSString,
                        arguments[3].toString() as NSString,
                        arguments[4].toString() as NSString,
                        arguments[5].toString() as NSString,
                        arguments[6].toString() as NSString,
                        arguments[7].toString() as NSString
                    )) as String
            9 -> NSString.create(format = macOsFormat,
                    args = *arrayOf(
                        arguments[0].toString() as NSString,
                        arguments[1].toString() as NSString,
                        arguments[2].toString() as NSString,
                        arguments[3].toString() as NSString,
                        arguments[4].toString() as NSString,
                        arguments[5].toString() as NSString,
                        arguments[6].toString() as NSString,
                        arguments[7].toString() as NSString,
                        arguments[8].toString() as NSString
                    )) as String
            else -> NSString.create(format = macOsFormat,
                    args = *arrayOf(
                        arguments[0].toString() as NSString,
                        arguments[1].toString() as NSString,
                        arguments[2].toString() as NSString,
                        arguments[3].toString() as NSString,
                        arguments[4].toString() as NSString,
                        arguments[5].toString() as NSString,
                        arguments[6].toString() as NSString,
                        arguments[7].toString() as NSString,
                        arguments[8].toString() as NSString,
                        arguments[9].toString() as NSString
                    )) as String
        }
    }

}
