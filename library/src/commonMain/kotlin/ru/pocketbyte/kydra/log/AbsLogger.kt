/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

abstract class AbsLogger: Logger() {

    override fun doLog(level: LogLevel, tag: String?, message: Any) {
        when(message) {
            is Throwable -> doLog(level, tag, exception = message)
            else -> doLog(level, tag, string = message.toString())
        }
    }

    protected abstract fun doLog(level: LogLevel, tag: String?, string: String)
    protected abstract fun doLog(level: LogLevel, tag: String?, exception: Throwable)
}
