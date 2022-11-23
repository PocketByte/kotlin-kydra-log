/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

import android.util.Log

/**
 * Android implementation of Logger that writes logs using LogCat.
 */
open class AndroidLogger: AbsLogger() {

    override fun log(level: LogLevel, tag: String?, string: String) {
        when(level) {
            LogLevel.INFO -> Log.i(tag ?: "", string)
            LogLevel.DEBUG -> Log.d(tag ?: "", string)
            LogLevel.WARNING -> Log.w(tag ?: "", string)
            LogLevel.ERROR -> Log.e(tag ?: "", string)
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