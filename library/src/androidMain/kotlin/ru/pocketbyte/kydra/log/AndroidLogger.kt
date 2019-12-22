/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

import android.util.Log

/**
 * Android implementation of Logger that writes logs using LogCat.
 */
open class AndroidLogger: Logger {

    override fun log(level: LogLevel, tag: String?, message: String) {
        when(level) {
            LogLevel.INFO -> Log.i(tag ?: "", message)
            LogLevel.DEBUG -> Log.d(tag ?: "", message)
            LogLevel.WARNING -> Log.w(tag ?: "", message)
            LogLevel.ERROR -> Log.e(tag ?: "", message)
        }
    }

    override fun log(level: LogLevel, tag: String?, exception: Throwable) {
        when(level) {
            LogLevel.INFO -> Log.i(tag ?: "", "", exception)
            LogLevel.DEBUG -> Log.d(tag ?: "", "", exception)
            LogLevel.WARNING -> Log.w(tag ?: "", "", exception)
            LogLevel.ERROR -> Log.e(tag ?: "", "", exception)
        }
    }

}