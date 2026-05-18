/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

import android.util.Log

/**
 * Android implementation of [Logger] that writes log records to Logcat.
 */
open class AndroidLogger: AbsLogger() {

    override fun doLog(level: LogLevel, tag: String?, string: String) {
        doLogInternal(level, tag, string, null)
    }

    override fun doLog(level: LogLevel, tag: String?, exception: Throwable) {
        doLogInternal(level, tag, null, exception)
    }

    override fun doLog(level: LogLevel, tag: String?, throwableWithMessage: ThrowableWithMessage) {
        doLogInternal(level, tag, throwableWithMessage.message, throwableWithMessage.throwable)
    }

    private fun doLogInternal(
        level: LogLevel,
        tag: String?,
        string: String?,
        exception: Throwable?
    ) {
        when(level) {
            LogLevel.INFO -> Log.i(tag, string, exception)
            LogLevel.DEBUG -> Log.d(tag, string, exception)
            LogLevel.WARNING -> Log.w(tag, string, exception)
            LogLevel.ERROR -> Log.e(tag, string, exception)
        }
    }
}
