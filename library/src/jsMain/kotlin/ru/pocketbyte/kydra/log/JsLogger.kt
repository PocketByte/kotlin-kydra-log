/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

/**
 * JavaScript implementation of Logger that writes logs using Console.
 */
open class JsLogger : AbsLogger() {

    override fun doLog(level: LogLevel, tag: String?, string: String) {
        when(level) {
            LogLevel.INFO -> console.info(logToString(tag, string))
            LogLevel.DEBUG -> console.log("DEBUG/${logToString(tag, string)}")
            LogLevel.WARNING -> console.warn(logToString(tag, string))
            LogLevel.ERROR ->console.error(logToString(tag, string))
        }
    }

    override fun doLog(level: LogLevel, tag: String?, exception: Throwable) {
        doLogThrowable(level, tag, null, exception)
    }

    override fun doLog(level: LogLevel, tag: String?, throwableWithMessage: ThrowableWithMessage) {
        doLogThrowable(level, tag, throwableWithMessage.message, throwableWithMessage.throwable)
    }

    private fun doLogThrowable(
        level: LogLevel,
        tag: String?,
        string: String?,
        exception: Throwable
    ) {
        when(level) {
            LogLevel.INFO -> console.info(logToString(tag, string), exception)
            LogLevel.DEBUG -> console.log("DEBUG/${logToString(tag, string)}", exception)
            LogLevel.WARNING -> console.warn(logToString(tag, string), exception)
            LogLevel.ERROR -> console.error(logToString(tag, string), exception)
        }
    }

    protected open fun logToString(tag: String?, message: String?): String {
        val builder = StringBuilder()

        if (tag?.isNotEmpty() == true)
            builder.append(tag).append(": ")

        if (message?.isNotEmpty() == true) {
            builder.append(message)
        }

        return builder.toString()
    }
}