/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

/**
 * JavaScript implementation of Logger that writes logs using Console.
 */
open class JsLogger : AbsLogger() {

    override fun log(level: LogLevel, tag: String?, string: String) {
        when(level) {
            LogLevel.INFO -> console.info(logToString(tag, string))
            LogLevel.DEBUG -> console.log("DEBUG/${logToString(tag, string)}")
            LogLevel.WARNING -> console.warn(logToString(tag, string))
            LogLevel.ERROR ->console.error(logToString(tag, string))
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