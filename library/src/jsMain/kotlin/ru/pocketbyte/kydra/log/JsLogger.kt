/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

/**
 * JavaScript implementation of Logger that writes logs using Console.
 */
open class JsLogger : Logger {

    override fun log(level: LogLevel, tag: String?, message: String) {
        when(level) {
            LogLevel.INFO -> console.info(logToString(tag, message))
            LogLevel.DEBUG -> console.log("DEBUG/${logToString(tag, message)}")
            LogLevel.WARNING -> console.warn(logToString(tag, message))
            LogLevel.ERROR ->console.error(logToString(tag, message))
        }
    }

    override fun log(level: LogLevel, tag: String?, exception: Throwable) {
        val logMessage = if (tag?.isNotEmpty() == true) "$tag: " else ""

        when(level) {
            LogLevel.INFO -> console.info(logMessage, exception)
            LogLevel.DEBUG -> console.log("DEBUG/$logMessage", exception)
            LogLevel.WARNING -> console.warn(logMessage, exception)
            LogLevel.ERROR ->console.error(logMessage, exception)
        }
    }

    protected open fun logToString(tag: String?, message: String): String {
        val builder = StringBuilder()

        if (tag?.isNotEmpty() == true)
            builder.append(tag).append(": ")

        builder.append(message)

        return builder.toString()
    }
}